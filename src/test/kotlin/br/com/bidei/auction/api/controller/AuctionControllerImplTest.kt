package br.com.bidei.auction.api.controller

import br.com.bidei.address.domain.repository.CitiesRepository
import br.com.bidei.address.domain.repository.StatesRepository
import br.com.bidei.auction.application.dto.AuctionDto
import br.com.bidei.auction.application.ports.AuctionService
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.customers.domain.repository.CustomersRepository
import br.com.bidei.factories.AddressFactory
import br.com.bidei.factories.AuctionFactory
import br.com.bidei.factories.CustomerFactory
import br.com.bidei.helper.PageableResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuctionControllerImplTest(@Autowired private val mockMvc: MockMvc,
                                @Autowired private val auctionService: AuctionService,
                                @Autowired private val auctionRepository: AuctionRepository,
                                @Autowired private val customersRepository: CustomersRepository,
                                @Autowired private val citiesRepository: CitiesRepository,
                                @Autowired private val statesRepository: StatesRepository,
                                @Autowired private val gson: Gson) {

    private inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object: TypeToken<T>() {}.type)


    private val validCustomer = CustomerFactory.newCustomer()
    private val validState = AddressFactory.newValidState()
    private val validCity = AddressFactory.newValidCity(validState)
    private val validAuction = AuctionFactory.newAuction(30, validCustomer,validCity, null)

    @BeforeAll
    fun setUp() {
        auctionRepository.deleteAll()
        customersRepository.deleteAll()
        citiesRepository.deleteAll()
        statesRepository.deleteAll()

        statesRepository.save(validState)
        citiesRepository.save(validCity)
        customersRepository.save(validCustomer)
        auctionRepository.save(validAuction)
    }

    @AfterAll
    fun tearDown(){
        auctionRepository.deleteAll()
        customersRepository.deleteAll()
        citiesRepository.deleteAll()
        statesRepository.deleteAll()
    }


    @Test
    @WithMockUser
    fun `When get on Auctions should list all auctions`(){
        val result =
                mockMvc.perform(MockMvcRequestBuilders.get("/public/api/v1/auctions"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val page: PageableResponse<AuctionDto> = gson.fromJson(result.response.contentAsString)

        assertEquals(page.content.size, 1)
        assertEquals(page.content.first().id, AuctionFactory.validAuctionId)
    }

    @Test
    @WithMockUser
    fun `When get on Auctions with a id should return a auction`(){
        val result =
                mockMvc.perform(MockMvcRequestBuilders.get("/public/api/v1/auctions/${validAuction.id}"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val auctionDto: AuctionDto = gson.fromJson(result.response.contentAsString)

        assertNotNull(auctionDto)
        assertEquals(auctionDto.id, AuctionFactory.validAuctionId)
    }

    @Test
    @WithMockUser
    fun `When get on Auctions with stateId should list all auctions of state`(){
        val result =
                mockMvc.perform(MockMvcRequestBuilders.get("/public/api/v1/auctions?stateId=35"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val page: PageableResponse<AuctionDto> = gson.fromJson(result.response.contentAsString)

        assertNotNull(page)
        assertEquals(page.content.first().id, AuctionFactory.validAuctionId)
    }

    @Test
    @WithMockUser
    fun `When get on Auctions with stateId should list all auctions of city`(){
        val result =
                mockMvc.perform(MockMvcRequestBuilders.get("/public/api/v1/auctions?cityId=3543402"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val page: PageableResponse<AuctionDto> = gson.fromJson(result.response.contentAsString)

        assertNotNull(page)
        assertEquals(page.content.first().id, AuctionFactory.validAuctionId)
    }

    @Test
    @WithMockUser
    fun `When post valid data should create new auction`(){
        auctionRepository.deleteAll()
        val auctionDto = auctionService.convertAuctionToCreateDto(validAuction)

        val result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("customerId", validCustomer.id)
                        .content(gson.toJson(auctionDto)))
                        .andExpect(MockMvcResultMatchers.status().isCreated)
                        .andReturn()

        val auctionDtoResult: AuctionDto = gson.fromJson(result.response.contentAsString, AuctionDto::class.java)

        assertNotNull(result)
        assertEquals(auctionDtoResult.id, validAuction.id)
    }

    @Test
    @WithMockUser
    fun `When put valid data should update a auction`(){
        val auctionDto = auctionService.convertAuctionToCreateDto(validAuction)

        val result =
                mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auctions/${validAuction.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("customerId", validCustomer.id)
                        .content(gson.toJson(auctionDto)))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val auctionDtoResult: AuctionDto = gson.fromJson(result.response.contentAsString, AuctionDto::class.java)

        assertNotNull(result)
        assertEquals(auctionDtoResult.id, validAuction.id)
    }

    @Test
    @WithMockUser
    fun `When put photo request should upload and associate photo to auction`(){
        val photoRequest = AuctionFactory.newAuctionPhotoDto()
        val result =
                mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auctions/${validAuction.id}/photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("customerId", validCustomer.id)
                        .content(gson.toJson(photoRequest)))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val auctionDtoResult: AuctionDto = gson.fromJson(result.response.contentAsString, AuctionDto::class.java)
        assertNotNull(result)
        assertEquals(auctionDtoResult.id, validAuction.id)
        assertNotNull(auctionDtoResult.photos)
        assertTrue(auctionDtoResult.photos?.size!! > 0)
    }

    @Test
    @WithMockUser
    fun `When delete photo request should upload and disassociate photo to auction`(){
        val auction = auctionService.getAuctionDtoById(validAuction.id!!)

        val result =
                mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/auctions/${validAuction.id}/photos/${auction.photos?.first()?.substringAfterLast("/")}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("customerId", validCustomer.id))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val auctionDtoResult: AuctionDto = gson.fromJson(result.response.contentAsString, AuctionDto::class.java)
        assertNotNull(result)
        assertEquals(auctionDtoResult.id, validAuction.id)
    }
}