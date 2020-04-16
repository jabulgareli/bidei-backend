package br.com.bidei.coupon.domain.dto

import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.coupon.domain.model.CouponTransaction
import br.com.bidei.coupon.domain.model.CouponType
import java.util.*

class CouponTransactionDto (
        val id: UUID,
        val code: String,
        val campaignName: String,
        val type: CouponType

){
    object Map {
        fun fromCouponTransaction(couponTransaction: CouponTransaction) =
                CouponTransactionDto(
                        couponTransaction.coupon.id!!,
                        couponTransaction.coupon.code,
                        couponTransaction.coupon.campaignName,
                        couponTransaction.coupon.type
                )
    }
}