package br.com.bidei.customers.application.adapters

import br.com.bidei.address.domain.model.City
import br.com.bidei.address.domain.model.State
import br.com.bidei.address.domain.repository.CitiesRepository
import br.com.bidei.address.domain.repository.StatesRepository
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.customers.domain.repository.CustomersRepository
import br.com.bidei.factories.CityFactory
import br.com.bidei.factories.CustomerFactory
import br.com.bidei.factories.StateFactory
import br.com.bidei.wallet.domain.ports.repository.WalletCustomerRepository
import com.google.gson.Gson
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
internal class CustomersControllerImplTest(
        @Autowired private val customersRepository: CustomersRepository,
        @Autowired private val citiesRepository: CitiesRepository,
        @Autowired private val statesRepository: StatesRepository,
        @Autowired private val gson: Gson,
        @Autowired private val mockMvc: MockMvc,
        @Autowired private val auctionRepository: AuctionRepository,
        @Autowired private val walletCustomerRepository: WalletCustomerRepository
) {

    @BeforeEach
    fun setUp() {
        walletCustomerRepository.deleteAll()
        auctionRepository.deleteAll()
        customersRepository.deleteAll()
        citiesRepository.deleteAll()
        statesRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
    }


    @Test
    @WithMockUser
    fun `Create customer with valid data`() {
        val state = statesRepository.save(State(10, "São Paulo", "SP", "North"))
        val city = citiesRepository.save(City(1L, "Ribeirão Preto", state))
        val customer = CustomerFactory.newCustomer()
        val customerDto = CustomerDto(customer.name, customer.email, customer.phone, city.id, customer.referenceId, customer.provider)

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(customerDto))
                                .accept(MediaType.ALL))
                .andExpect(status().isOk)

        assertEquals(customersRepository.findAll().size, 1)
    }

    @Test
    @WithMockUser
    fun `Do not create customer with invalid city id`() {
        val customer = CustomerFactory.newCustomer()
        val customerDto = CustomerDto(customer.name, customer.email, customer.phone, 5L, customer.referenceId, customer.provider)

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(customerDto))
                                .accept(MediaType.ALL))
                .andExpect(status().isNotFound)

        assertEquals(customersRepository.findAll().size, 0)
    }

    @Test
    @WithMockUser
    fun `Do not create customer with invalid provider`() {
        val customer = CustomerFactory.newCustomer()
        val customerDto = CustomerDto(customer.name, customer.email, customer.phone, 5L, customer.referenceId, "NO_PROVIDER")

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(customerDto))
                                .accept(MediaType.ALL))
                .andExpect(status().isNotFound)

        assertEquals(customersRepository.findAll().size, 0)
    }

    @Test
    @WithMockUser
    fun `Update customer with valid data`() {
        statesRepository.save(StateFactory.newState())
        citiesRepository.save(CityFactory.newCity())
        val oldCustomer = customersRepository.save(CustomerFactory.newCustomer())
        val newState = statesRepository.save(State(10, "São Paulo", "SP", "North"))
        val newCity = citiesRepository.save(City(1L, "Ribeirão Preto", newState))
        val customerUpdateDto = CustomerUpdateDto(name = "new name", cityId = newCity.id)

        val result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/customers")
                                .header("customerId", oldCustomer.id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(customerUpdateDto))
                                .accept(MediaType.ALL))
                .andExpect(status().isOk)
                .andReturn()

        val updatedCustomer = gson.fromJson(result.response.contentAsString, Customer::class.java)
        assertEquals(updatedCustomer.name, customerUpdateDto.name)
        assertEquals(updatedCustomer.city.id, customerUpdateDto.cityId)
    }

    @Test
    @WithMockUser
    fun `Do not update customer with invalid city id`() {
        val customerUpdateDto = CustomerUpdateDto(name = "new name", cityId = 1L)
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/v1/customers")
                                .header("customerId", CustomerFactory.customerId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(customerUpdateDto))
                                .accept(MediaType.ALL))
                .andExpect(status().isNotFound)
    }

    @Test
    fun `When customer exists should return http 200`() {
        statesRepository.save(StateFactory.newState())
        citiesRepository.save(CityFactory.newCity())
        customersRepository.save(CustomerFactory.newCustomer())
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/public/api/v1/customers")
                                .param("email", CustomerFactory.email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.ALL))
                .andExpect(status().isOk)
    }

    @Test
    fun `When customer not exists should return http 404`() {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/public/api/v1/customers")
                                .param("email", CustomerFactory.email)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.ALL))
                .andExpect(status().isNotFound)
    }

}