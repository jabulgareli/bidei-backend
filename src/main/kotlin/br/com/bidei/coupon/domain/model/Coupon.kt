package br.com.bidei.coupon.domain.model

import br.com.bidei.coupon.domain.exception.ExpiredCouponException
import br.com.bidei.coupon.domain.exception.LimitUsageExceededException
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Coupon(
        @Id
        val id: UUID? = UUID.randomUUID(),
        val code: String,
        val bidPrize: Int,
        val usesPerCustomer: Int,
        val quantity: Int,
        var used: Int,
        val expiresIn: Timestamp,
        val type: CouponType,
        val campaignName: String,
        @OneToOne
        val partner: Customer,
        val partnerPrizePercent: BigDecimal? = BigDecimal.ZERO,
        val partnerPrizeDaysLimit: Int) {

    fun canBeUsed(currentUsagePerUser: Int) {
        if (expiresIn <= DateUtils.utcNow())
            throw ExpiredCouponException()

        if (usesPerCustomer in 1..currentUsagePerUser)
            throw LimitUsageExceededException()

        if (quantity in 1..used)
            throw LimitUsageExceededException()
    }

    fun incUsed() {
        used += 1
    }

    fun isInvite() = this.type == CouponType.INVITE && this.partnerPrizeDaysLimit > 0

    fun isValidInvite(invitedAt: Timestamp) =
            DateUtils.addDays(invitedAt, partnerPrizeDaysLimit) >= DateUtils.utcNow()


    fun getPrizeAmount(amountPurchased: BigDecimal) =
            partnerPrizePercent?.divide(BigDecimal.valueOf(100))?.multiply(amountPurchased)

}