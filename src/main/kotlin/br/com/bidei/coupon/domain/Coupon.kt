package br.com.bidei.coupon.domain

import br.com.bidei.customers.domain.model.Customer
import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Coupon(
        @Id
        val id: UUID? = UUID.randomUUID(),
        val bidPrize: Int,
        val usesPerCustomer: Int,
        val quantity: Int,
        val used: Int,
        val expiresIn: Date,
        val type: CouponType,
        val campaignName: String,
        @OneToOne
        val partner: Customer,
        val partnerPrizePercent: BigDecimal? = BigDecimal.ZERO,
        val partnerPrizeDaysLimit: Int)