package br.com.bidei.customers.application.ports

import br.com.bidei.customers.domain.dto.CustomerDto
import br.com.bidei.customers.domain.dto.CustomerProviderDto
import br.com.bidei.customers.domain.dto.CustomerUpdateDto
import br.com.bidei.customers.domain.model.Customer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

private const val PUBLIC = "/public/api/v1"
private const val PRIVATE = "/api/v1"

@RequestMapping
interface CustomersController {

    @PostMapping("$PRIVATE/customers")
    fun create(@RequestBody customer: CustomerDto): ResponseEntity<Customer>

    @PutMapping("$PRIVATE/customers")
    fun update(@RequestHeader("customerId") customerId: UUID, @RequestBody customer: CustomerUpdateDto): ResponseEntity<Customer>

    @GetMapping("$PUBLIC/customers")
    fun findByEmail(@RequestParam email: String): CustomerProviderDto

}