package br.com.bidei.acl.ports

import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import java.util.*

interface CustomersAclPort {
    fun findById(id: UUID): Optional<Customer>
    fun receiveInviteFromPartner(customer: Customer, coupon: Coupon)
}