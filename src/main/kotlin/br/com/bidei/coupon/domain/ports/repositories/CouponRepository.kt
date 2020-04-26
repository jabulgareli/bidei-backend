package br.com.bidei.coupon.domain.ports.repositories

import br.com.bidei.coupon.domain.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponRepository: JpaRepository<Coupon, UUID> {
    fun findByCode(code: String): Optional<Coupon>
}