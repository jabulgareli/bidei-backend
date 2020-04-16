package br.com.bidei.acl.ports

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal

interface CouponAclPort {
    fun prizePartner(customer: Customer, bidsPurchasedAmount: BigDecimal, statement: WalletStatement)
}