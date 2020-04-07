package br.com.bidei.acl.adapters

import br.com.bidei.customers.application.services.CustomersService
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.acl.ports.CustomersAclPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomersAclAdapter(private val customersService: CustomersService) : CustomersAclPort {

    override fun findById(id: UUID): Optional<Customer> {
        return customersService.findById(id)
    }

}