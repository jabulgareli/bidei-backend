package br.com.bidei.coupon.repository

import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.coupon.domain.model.CouponTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponTransactionRepository : JpaRepository<CouponTransaction, UUID> {
    fun countByCustomerIdAndCoupon_Code(customerId: UUID, code: String): Int
}