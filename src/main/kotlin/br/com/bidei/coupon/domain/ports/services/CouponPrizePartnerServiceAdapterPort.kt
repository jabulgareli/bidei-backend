package br.com.bidei.coupon.domain.ports.services

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal

interface CouponPrizePartnerServiceAdapterPort {
    fun prizePartner(customer: Customer, bidsPurchasedAmount: BigDecimal, statement: WalletStatement)
}