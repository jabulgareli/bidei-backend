package br.com.bidei.coupon.domain.ports.repositories

import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.coupon.domain.model.CouponPartnerPrize
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CouponPartnerPrizeRepository: JpaRepository<CouponPartnerPrize, UUID> {

}