package br.com.bidei.bid.api.controller

import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.address.domain.ports.repositories.CitiesRepository
import br.com.bidei.address.domain.ports.repositories.StatesRepository
import br.com.bidei.auction.domain.ports.services.AuctionService
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.bid.domain.ports.repositories.BidRepository
import br.com.bidei.customers.domain.ports.repositories.CustomersRepository
import br.com.bidei.factories.*
import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeRequest
import br.com.bidei.wallet.domain.ports.repositories.WalletCustomerRepository
import br.com.bidei.wallet.domain.ports.repositories.WalletStatementRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
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
class BidControllerImplTest(@Autowired private val walletStatementRepository: WalletStatementRepository,
                            @Autowired private val walletCustomerRepository: WalletCustomerRepository,
                            @Autowired private val customersRepository: CustomersRepository,
                            @Autowired private val citiesRepository: CitiesRepository,
                            @Autowired private val statesRepository: StatesRepository,
                            @Autowired private val gson: Gson,
                            @Autowired private val mockMvc: MockMvc,
                            @Autowired private val auctionRepository: AuctionRepository,
                            @Autowired private val auctionService: AuctionService,
                            @Autowired private val bidRepository: BidRepository) {

    private inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(json, object : TypeToken<T>() {}.type)

    private val validCustomer = CustomerFactory.newCustomer()
    private val validState = AddressFactory.newValidState()
    private val validCity = AddressFactory.newValidCity(validState)
    private val validAuction = AuctionFactory.newAuction(30, validCustomer, validCity, null)

    @MockBean
    private lateinit var integrationsPaymentsAcl: IntegrationsPaymentsAclPort;

    private fun chargeWallet() {
        val walletCardChargeDto = WalletFactory.newWalletCardChargeDto()
        val walletCustomer = WalletCustomerFactory.newWalletCustomer()

        BDDMockito.given(integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))).willReturn(IuguFactory.newIuguChargeResponse(true))

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/wallet/card/transaction")
                .header("customerId", CustomerFactory.customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(walletCardChargeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @BeforeEach
    fun setUp() {
        bidRepository.deleteAll()
        auctionRepository.deleteAll()
        walletStatementRepository.deleteAll()
        walletCustomerRepository.deleteAll()
        customersRepository.deleteAll()
        citiesRepository.deleteAll()
        statesRepository.deleteAll()


        statesRepository.save(validState)
        citiesRepository.save(validCity)
        customersRepository.save(validCustomer)
        walletCustomerRepository.save(WalletCustomerFactory.newWalletCustomer())
        auctionRepository.save(validAuction)
    }

    @Test
    @WithMockUser
    fun `When bid an auction wallet should receive CREATED`() {
        chargeWallet()

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bid")
                .header("customerId", validCustomer.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(BidFactory.newValidBidRequest())))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()

    }

    @Test
    @WithMockUser
    fun `When bid without balance should receive PAYMENT_REQUIRED`() {
       val result =  mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bid")
                .contentType(MediaType.APPLICATION_JSON)
                .header("customerId", validCustomer.id)
                .content(gson.toJson(BidFactory.newValidBidRequest())))
                .andExpect(MockMvcResultMatchers.status().isPaymentRequired)
                .andReturn()

        assertEquals("Insufficient balance on wallet", result.response.errorMessage)

    }

    @Test
    @WithMockUser
    fun `When bid and price has changed should receive CONFLICT`() {
        chargeWallet()

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bid")
                .contentType(MediaType.APPLICATION_JSON)
                .header("customerId", validCustomer.id)
                .content(gson.toJson(BidFactory.newValidBidRequest())))
                .andExpect(MockMvcResultMatchers.status().isCreated)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("customerId", validCustomer.id)
                            .content(gson.toJson(BidFactory.newValidBidRequest())))
                            .andExpect(MockMvcResultMatchers.status().isConflict)
                            .andReturn()


        assertEquals("Price has changed", result.response.errorMessage)
    }

    @Test
    @WithMockUser
    fun `When bid a finished auction should receive CONFLICT`() {
        auctionService.finish(validAuction.customer.id, validAuction.id!!)

        val result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/bid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("customerId", validCustomer.id)
                            .content(gson.toJson(BidFactory.newValidBidRequest())))
                            .andExpect(MockMvcResultMatchers.status().isConflict)
                            .andReturn()

        assertEquals("Auction is finished", result.response.errorMessage)
    }
}