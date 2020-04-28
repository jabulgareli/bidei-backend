package br.com.bidei.customers.domain.ports.services

import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerProviderDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import java.util.*

interface CustomersService {
    fun findById(id: UUID): Optional<Customer>
    fun create(customerDto: CustomerDto): Customer
    fun update(customerUpdateDto: CustomerUpdateDto): Customer
    fun findByEmail(email: String): CustomerProviderDto
    fun findByReferenceId(referenceId: String): Customer
}