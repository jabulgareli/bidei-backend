package br.com.bidei.customers.application.adapters

import br.com.bidei.acl.ports.AddressAclPort
import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.customers.domain.exceptions.CityNotFoundException
import br.com.bidei.customers.domain.exceptions.CustomerAlreadyRegisteredException
import br.com.bidei.customers.domain.exceptions.CustomerNotFoundException
import br.com.bidei.customers.domain.exceptions.CustomerProviderNotFoundException
import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerProviderDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.customers.domain.ports.repositories.CustomersRepository
import br.com.bidei.customers.domain.ports.services.CustomersService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomersServiceImpl(
        private val customersRepository: CustomersRepository,
        private val addressAclPort: AddressAclPort,
        private val walletAclPort: WalletAclPort
) : CustomersService {

    override fun findById(id: UUID): Optional<Customer> = customersRepository.findById(id)

    private fun customerExistsByEmail(email: String) = customersRepository.findByEmail(email)

    private fun customerExistsById(id: UUID) = customersRepository.findById(id)

    private fun customerExistsByReferenceId(referenceId: String) = customersRepository.findByReferenceId(referenceId)

    override fun create(customerDto: CustomerDto): Customer {
        val city = addressAclPort.findCityById(customerDto.cityId)

        if (!city.isPresent)
            throw CityNotFoundException()

        if (!customerDto.isValidProvider())
            throw CustomerProviderNotFoundException()

        if (customerExistsByReferenceId(customerDto.referenceId).isPresent)
            throw CustomerAlreadyRegisteredException()

        if (customerExistsByEmail(customerDto.email).isPresent)
            throw CustomerAlreadyRegisteredException()

        val customer = customersRepository.save(customerDto.toCustomer(city.get()))

        walletAclPort.create(customer)

        return customer
    }

    override fun update(customerUpdateDto: CustomerUpdateDto): Customer {
        val city = addressAclPort.findCityById(customerUpdateDto.cityId)
        if (!city.isPresent) throw CityNotFoundException()
        val updatedCustomer = customerExistsById(customerUpdateDto.customerId!!).get()
        updatedCustomer.name = customerUpdateDto.name
        updatedCustomer.city = city.get()
        return customersRepository.save(updatedCustomer)
    }

    override fun findByEmail(email: String): CustomerProviderDto {
        val customer = customerExistsByEmail(email)
        if (!customer.isPresent) throw CustomerNotFoundException()
        return CustomerProviderDto(customer.get().provider)
    }

    override fun findByReferenceId(referenceId: String): Customer {
        val customer = customersRepository.findByReferenceId(referenceId)
        if (!customer.isPresent) throw CustomerNotFoundException()
        return customer.get()
    }

}