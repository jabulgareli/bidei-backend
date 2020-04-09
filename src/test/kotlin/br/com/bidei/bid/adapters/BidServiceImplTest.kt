package br.com.bidei.bid.adapters

import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.bid.application.ports.BidService
import br.com.bidei.bid.domain.exception.PriceChangedException
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.repository.BidRepository
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.customers.domain.repository.CustomersRepository
import br.com.bidei.factories.*
import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeRequest
import br.com.bidei.utils.DateUtils
import br.com.bidei.wallet.domain.exceptions.InsufficientBalanceOnWalletException
import br.com.bidei.wallet.domain.model.WalletStatement
import br.com.bidei.wallet.domain.ports.repository.WalletCustomerRepository
import br.com.bidei.wallet.domain.ports.repository.WalletStatementRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BidServiceImplTest(@Autowired val bidService: BidService) {

    @MockBean
    private lateinit var auctionRepository: AuctionRepository
    @MockBean
    private lateinit var customerRepository: CustomersRepository
    @MockBean
    private lateinit var walletCustomerRepository: WalletCustomerRepository
    @MockBean
    private lateinit var bidRepository: BidRepository
    @MockBean
    private lateinit var walletStatementRepository: WalletStatementRepository

    private val customer: Customer =  CustomerFactory.newCustomer()
    private val auction = AuctionFactory.newAuction(5, customer, CityFactory.newCity())
    private val wallet = WalletFactory.newWalletCustomer(customer)
    private val bidRequest = BidFactory.newValidBidRequest()
    private val bid = Bid(auction = auction, customer = customer, value = BidFactory.defaultBid, priceOnBid = auction.startPrice, createdDate = DateUtils.utcNow())


    @BeforeEach
    fun setUp(){
        given(auctionRepository.findById(AuctionFactory.validAuctionId)).willReturn(Optional.of(auction))
        given(customerRepository.findById(CustomerFactory.customerId)).willReturn(Optional.of(customer))
        given(walletCustomerRepository.findByCustomerId(CustomerFactory.customerId)).willReturn(Optional.of(wallet))
        given(walletCustomerRepository.save(wallet)).willReturn(wallet)
        given(walletStatementRepository.save(Mockito.any(WalletStatement::class.java))).willReturn(null)
        given(bidRepository.findByCustomerIdAndAuctionIdAndValueAndPriceOnBidAndCreatedDateGreaterThanEqual(customer.id, auction.id!!,
                bidRequest.value!!, bidRequest.currentPrice!!, DateUtils.addMinutes(DateUtils.utcNow(), -5) ))
                .willReturn(Optional.of(bid))

        given(auctionRepository.acceptBid(auction.id!!, bidRequest.value!!, bidRequest.currentPrice!!)).willReturn(1)

    }

    @Test
    fun `When bid an auction and another customer already send an bid should throws exception`(){
        given(auctionRepository.acceptBid(auction.id!!, bidRequest.value!!, bidRequest.currentPrice!!)).willReturn(0)
        wallet.chargeWallet(BigDecimal.ONE)

        try {
            bidService.newBid(bidRequest)
            fail("PriceChangedException expected")
        }catch (e: PriceChangedException){

        }catch (e: Exception){
            fail("PriceChangedException expected")
        }
    }

    @Test
    fun `When bid an auction without balance should throws exception`(){
        val previousPrice = auction.currentPrice

        try {
            bidService.newBid(bidRequest)
            fail("InsufficientBalanceOnWalletException expected")
        }catch (e: InsufficientBalanceOnWalletException){

        }catch (e: Exception){
            fail("InsufficientBalanceOnWalletException expected")
        }


        assertEquals(wallet.bids, BigDecimal.ZERO)
        assertEquals(auction.currentPrice, previousPrice)
    }

}