package br.com.bidei.customers.application.services

import br.com.bidei.acl.ports.AddressAclPort
import br.com.bidei.customers.application.exceptions.CityNotFoundException
import br.com.bidei.customers.application.exceptions.CustomerAlreadyRegisteredException
import br.com.bidei.customers.application.exceptions.CustomerNotFoundException
import br.com.bidei.customers.application.exceptions.CustomerProviderNotFoundException
import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerProviderDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.customers.domain.repository.CustomersRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomersServiceImpl(
        private val customersRepository: CustomersRepository,
        private val addressAclPort: AddressAclPort
) : CustomersService {

    override fun findById(id: UUID): Optional<Customer> = customersRepository.findById(id)

    private fun customerExistsByEmail(email: String) = customersRepository.findByEmail(email)

    private fun customerExistsById(id: UUID) = customersRepository.findById(id)

    private fun customerExistsByReferenceId(referenceId: String) = customersRepository.findByReferenceId(referenceId)

    override fun create(customer: CustomerDto): Customer {
        val city = addressAclPort.findCityById(customer.cityId)

        if (!city.isPresent)
            throw CityNotFoundException()

        if (!customer.isValidProvider())
            throw CustomerProviderNotFoundException()

        if (customerExistsByReferenceId(customer.referenceId).isPresent)
            throw CustomerAlreadyRegisteredException()

        if (customerExistsByEmail(customer.email).isPresent)
            throw CustomerAlreadyRegisteredException()

        return  customersRepository.save(customer.toCustomer(city.get()))
    }

    override fun update(customer: CustomerUpdateDto): Customer {
        val city = addressAclPort.findCityById(customer.cityId)
        if (!city.isPresent) throw CityNotFoundException()
        val updatedCustomer = customerExistsById(customer.customerId!!).get()
        updatedCustomer.name = customer.name
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