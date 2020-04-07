package br.com.bidei.customers.application.services

import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerProviderDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import java.util.*

interface CustomersService {
    fun findById(id: UUID): Optional<Customer>
    fun create(customer: CustomerDto): Customer
    fun update(customer: CustomerUpdateDto): Customer
    fun findByEmail(email: String): CustomerProviderDto
}