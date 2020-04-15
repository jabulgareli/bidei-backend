package br.com.bidei.acl.ports

import br.com.bidei.customers.domain.model.Customer
import java.util.*

interface CustomersAclPort {
    fun findById(id: UUID): Optional<Customer>
    fun receiveInviteFromPartner(customer: Customer, partner: Customer)
}