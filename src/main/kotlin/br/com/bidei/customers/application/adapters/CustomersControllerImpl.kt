package br.com.bidei.customers.application.adapters

import br.com.bidei.customers.application.ports.CustomersController
import br.com.bidei.customers.application.services.CustomersService
import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CustomersControllerImpl(
        private val customersService: CustomersService
) : CustomersController {

    override fun create( customer: CustomerDto) = ResponseEntity.ok(customersService.create(customer))

    override fun update(customerId: UUID, customer: CustomerUpdateDto): ResponseEntity<Customer> {
        customer.customerId = customerId
        return ResponseEntity.ok(customersService.update(customer))
    }

    override fun findByEmail(email: String) = customersService.findByEmail(email)

    override fun findByReferenceId(referenceId: String) = customersService.findByReferenceId(referenceId)

}