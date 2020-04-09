package br.com.bidei.wallet.api.controller

import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.address.domain.repository.CitiesRepository
import br.com.bidei.address.domain.repository.StatesRepository
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.customers.domain.repository.CustomersRepository
import br.com.bidei.factories.*
import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeRequest
import br.com.bidei.integrations.payments.infrastructure.dto.IuguCustomerRequest
import br.com.bidei.wallet.domain.dto.PaymentMethodsDto
import br.com.bidei.wallet.domain.ports.repository.WalletCustomerRepository
import br.com.bidei.wallet.domain.ports.repository.WalletStatementRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
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
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
internal class WalletControllerImplTest(
        @Autowired private val iuguConfig: IuguConfig,
        @Autowired private val mockMvc: MockMvc,
        @Autowired private val gson: Gson,
        @Autowired private val statesRepository: StatesRepository,
        @Autowired private val citiesRepository: CitiesRepository,
        @Autowired private val customersRepository: CustomersRepository,
        @Autowired private val walletCustomerRepository: WalletCustomerRepository,
        @Autowired private val walletStatementRepository: WalletStatementRepository,
        @Autowired private val auctionRepository: AuctionRepository
) {

    private inline fun <reified T> Gson.fromJson(json: String?) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    @MockBean
    lateinit var integrationsPaymentsAcl: IntegrationsPaymentsAclPort

    @BeforeEach
    fun setUp() {
        auctionRepository.deleteAll()
        walletStatementRepository.deleteAll()
        walletCustomerRepository.deleteAll()
        customersRepository.deleteAll()
        citiesRepository.deleteAll()
        statesRepository.deleteAll()

        statesRepository.save(StateFactory.newState())
        citiesRepository.save(CityFactory.newCity())
        customersRepository.save(CustomerFactory.newCustomer())
        walletCustomerRepository.save(WalletCustomerFactory.newWalletCustomer())
    }

    @Test
    @WithMockUser
    fun `When get on wallet customer payment-methods should show list payment methods`() {
        val customer = CustomerFactory.newCustomer()

        given(integrationsPaymentsAcl.listPaymentMethods(WalletCustomerFactory.referenceId)).willReturn(listOf(IuguFactory.newIuguPaymentMethodsResponse()))

        val result =
                mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/wallet/${customer.id}/payment-methods"))
                        .andExpect(MockMvcResultMatchers.status().isOk)
                        .andReturn()

        val paymentMethods: ArrayList<PaymentMethodsDto> = gson.fromJson(result.response.contentAsString)
        assertNotNull(result.response.contentAsString)
        assertEquals(paymentMethods.size, 1)
    }

    @Test
    @WithMockUser
    fun `When valid data and customer and wallet-customer already exists should create payment method`() {
        val createCardDto = WalletFactory.newCreateCardDto()

        given(integrationsPaymentsAcl.createPaymentToken(IuguFactory.newIuguPaymentTokenRequest(iuguConfig))).willReturn(IuguFactory.newIuguPaymentTokenResponse())
        given(integrationsPaymentsAcl.createPaymentMethods(IuguFactory.customerId.toString(), IuguFactory.newIuguPaymentMethodsRequest())).willReturn(true)

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/payment-methods")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createCardDto)))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    @WithMockUser
    fun `When invalid valid data should return http 400 error`() {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/payment-methods")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson("")))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    @WithMockUser
    fun `When request without body should return http 400 error`() {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/payment-methods")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    @WithMockUser
    fun `When valid data and customer exists but wallet-customer not exists should create wallet-customer and payment method`() {
        walletCustomerRepository.deleteAll()
        val createCardDto = WalletFactory.newCreateCardDto()

        given(integrationsPaymentsAcl.createCustomer(IuguCustomerRequest.Map.from(CustomerFactory.newCustomer()))).willReturn(IuguFactory.newIuguCustomerResponse())
        given(integrationsPaymentsAcl.createPaymentToken(IuguFactory.newIuguPaymentTokenRequest(iuguConfig))).willReturn(IuguFactory.newIuguPaymentTokenResponse())
        given(integrationsPaymentsAcl.createPaymentMethods(IuguFactory.customerId.toString(), IuguFactory.newIuguPaymentMethodsRequest())).willReturn(true)

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/payment-methods")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(createCardDto)))
                .andExpect(MockMvcResultMatchers.status().isOk)

        assertEquals(walletCustomerRepository.findAll().size, 1)
    }

    @Test
    @WithMockUser
    fun `When valid data should delete payment method`() {
        val customer = CustomerFactory.newCustomer()
        val paymentMethod = IuguFactory.newIuguPaymentMethodsResponse()
        val wallet = WalletFactory.newWalletCustomer(customer)

        given(integrationsPaymentsAcl.removePaymentMethods(wallet.referenceId, paymentMethod.id.toString())).willReturn(true)

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/wallet/${customer.id}/payment-methods/${paymentMethod.id}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
    }

    @Test
    @WithMockUser
    fun `When valid data and valid card should create new card transaction with success`() {
        val walletCardChargeDto = WalletFactory.newWalletCardChargeDto()
        val walletCustomer = WalletCustomerFactory.newWalletCustomer()

        given(integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))).willReturn(IuguFactory.newIuguChargeResponse(true))

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/card/transaction")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(walletCardChargeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk)

        assertEquals(walletStatementRepository.findAll().size, 1)
        assertEquals(walletStatementRepository.findAll()[0].success, true)
        assertEquals(walletCustomerRepository.findAll()[0].bids, walletCardChargeDto.getBidsQuantity().setScale(2))
    }

    @Test
    @WithMockUser
    fun `When valid data and invalid card should create new card transaction without success`() {
        val walletCardChargeDto = WalletFactory.newWalletCardChargeDto()
        val walletCustomer = WalletCustomerFactory.newWalletCustomer()

        given(integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))).willReturn(IuguFactory.newIuguChargeResponse(false))

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/wallet/card/transaction")
                        .header("customerId", CustomerFactory.customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(walletCardChargeDto)))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val newWalletCustomer = walletCustomerRepository.findAll()[0]

        assertEquals(walletStatementRepository.findAll().size, 1)
        assertEquals(walletStatementRepository.findAll()[0].success, false)
        assertNotEquals(newWalletCustomer.bids, walletCardChargeDto.getBidsQuantity().setScale(2))
        assertEquals(newWalletCustomer.bids, walletCustomer.bids.setScale(2))
    }
}