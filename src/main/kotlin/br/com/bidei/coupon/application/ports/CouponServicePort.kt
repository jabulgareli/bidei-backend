package br.com.bidei.coupon.application.ports

import br.com.bidei.coupon.domain.dto.CouponTransactionDto
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal
import java.util.*

interface CouponServicePort {
    fun apply(customerId: UUID, code: String)
    fun getUsedCouponsByCustomerId(customerId: UUID): List<CouponTransactionDto>
}