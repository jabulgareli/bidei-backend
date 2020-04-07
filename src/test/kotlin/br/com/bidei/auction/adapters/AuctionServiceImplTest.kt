package br.com.bidei.auction.adapters

import br.com.bidei.acl.ports.AddressAclPort
import br.com.bidei.auction.application.ports.AuctionService
import br.com.bidei.auction.domain.exceptions.AuctionAlreadyFinishedException
import br.com.bidei.auction.domain.exceptions.AuctionNotFoundException
import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.auction.application.dto.CreateOrUpdateAuctionDto
import br.com.bidei.cross.exceptions.EntityNotOwnedByCustomer
import br.com.bidei.factories.AddressFactory
import br.com.bidei.factories.AuctionFactory
import br.com.bidei.factories.CustomerFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.lang.Exception
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuctionServiceImplTest(
        @Autowired private val auctionService: AuctionService) {

    private lateinit var validAuctionDto: CreateOrUpdateAuctionDto;
    private val validState = AddressFactory.newValidState()
    private val validCity = AddressFactory.newValidCity(validState)
    private val validCustomer = CustomerFactory.newCustomer()
    private val validAuction = AuctionFactory.newAuction(30, validCustomer, validCity)
    private val manuallyFinishedAuction = AuctionFactory.newAuction(30, validCustomer, validCity, 1)

    @MockBean
    private lateinit var auctionRepository: AuctionRepository;
    @MockBean
    private lateinit var  customerAcl: CustomersAclPort
    @MockBean
    private lateinit var  addressAclPort: AddressAclPort;

    @BeforeEach
    fun setUp(){
        validAuctionDto = auctionService.convertAuctionToCreateDto(validAuction)
    }

    @Test
    fun `Valid auction must be persisted`(){
        given(auctionRepository.save(Mockito.any(Auction::class.java))).willReturn(validAuction)
        given(customerAcl.findById(CustomerFactory.customerId)).willReturn(Optional.of(validCustomer))
        given(addressAclPort.findCityById(AddressFactory.validCityId)).willReturn(Optional.of(validCity))

        val auction = auctionService.create(validAuctionDto)

        //then(auctionRepository).should()?.save(validAuction!!) Nao dá por conta da conversao para dto (fica referencia diferente)

        Assertions.assertNotNull(auction)
        Assertions.assertEquals(validAuctionDto.id, auction.id)
    }

    @Test
    fun `Auctions can be searched by id`(){
        given(auctionRepository.findById(AuctionFactory.validAuctionId)).willReturn(Optional.of(validAuction))

        val auction = auctionService.getAuctionDtoById(AuctionFactory.validAuctionId)

        then(auctionRepository).should().findById(AuctionFactory.validAuctionId)

        Assertions.assertNotNull(auction)
    }

    @Test
    fun `Searches for an invalid auction id throws exception`(){
        val id = UUID.randomUUID()
        given(auctionRepository.findById(id)).willReturn(Optional.empty())

        try {
            auctionService.getAuctionDtoById(id)
            Assertions.fail<Auction>("AuctionNotFoundException expected")
        }catch (e: AuctionNotFoundException){

        }catch (e: Exception){
            Assertions.fail<Auction>("AuctionNotFoundException expected")
        }

        then(auctionRepository).should().findById(id)
    }

    @Test
    fun `An auction can be manually finished`(){
        given(auctionRepository.findById(AuctionFactory.validAuctionId)).willReturn(Optional.of(validAuction))
        given(auctionRepository.save(Mockito.any(Auction::class.java))).willReturn(validAuction)
        given(auctionRepository.findByIdAndCustomerId(AuctionFactory.validAuctionId, validCustomer.id)).willReturn(Optional.of(validAuction))

        val auction = auctionService.finish(validCustomer.id, AuctionFactory.validAuctionId)

        Assertions.assertNotNull(auction.manuallyFinishedAt)

    }

    @Test
    fun `An auction can't be manually finished with other owner`(){
        given(auctionRepository.findById(AuctionFactory.validAuctionId)).willReturn(Optional.of(validAuction))
        given(auctionRepository.save(Mockito.any(Auction::class.java))).willReturn(validAuction)
        given(auctionRepository.findByIdAndCustomerId(AuctionFactory.validAuctionId, validCustomer.id)).willReturn(Optional.of(validAuction))

        try {
            auctionService.finish(UUID.randomUUID(), AuctionFactory.validAuctionId)
            Assertions.fail<Auction>("EntityNotOwnedByCustomer expected")
        }catch (e: EntityNotOwnedByCustomer){

        }catch (e: Exception){
            Assertions.fail<Auction>("EntityNotOwnedByCustomer expected")
        }

        then(auctionRepository.findByIdAndCustomerId(AuctionFactory.validAuctionId, validCustomer.id))

    }

    @Test
    fun `Nonexistent auction can't be manually finished`(){
        val id = UUID.randomUUID()
        given(auctionRepository.findById(id)).willReturn(Optional.empty())
        given(auctionRepository.save(Mockito.any(Auction::class.java))).willReturn(validAuction)
        given(auctionRepository.findByIdAndCustomerId(id, validCustomer.id)).willReturn(Optional.of(validAuction))

        try {
            auctionService.finish(validCustomer.id, id)
            Assertions.fail<Auction>("AuctionNotFoundException expected")
        }catch (e: AuctionNotFoundException){

        }catch (e: Exception){
            Assertions.fail<Auction>("AuctionNotFoundException expected")
        }

        then(auctionRepository).should().findById(id)
        then(auctionRepository).should().findByIdAndCustomerId(id, validCustomer.id)
        then(auctionRepository).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `An auction already finished can't be finished`(){
        val id = UUID.randomUUID()
        given(auctionRepository.findById(id)).willReturn(Optional.of(manuallyFinishedAuction))
        given(auctionRepository.save(Mockito.any(Auction::class.java))).willReturn(validAuction )
        given(auctionRepository.findByIdAndCustomerId(id, validCustomer.id)).willReturn(Optional.of(validAuction))

        try {
            auctionService.finish(validCustomer.id, id)
            Assertions.fail<Auction>("AuctionAlreadyFinishedException expected")
        }catch (e: AuctionAlreadyFinishedException){

        }catch (e: Exception){
            Assertions.fail<Auction>("AuctionAlreadyFinishedException expected")
        }

        then(auctionRepository).should().findById(id)
        then(auctionRepository).should().findByIdAndCustomerId(id, validCustomer.id)
        then(auctionRepository).shouldHaveNoMoreInteractions()
    }
}