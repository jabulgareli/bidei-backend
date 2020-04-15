package br.com.bidei.coupon.repository

import br.com.bidei.coupon.domain.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponRepository: JpaRepository<Coupon, UUID> {
}