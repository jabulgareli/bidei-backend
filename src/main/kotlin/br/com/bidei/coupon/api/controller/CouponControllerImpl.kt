package br.com.bidei.coupon.api.controller

import br.com.bidei.coupon.domain.ports.services.CouponServicePort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CouponControllerImpl(private val couponServicePort: CouponServicePort) : CouponController {
    override fun apply(customerId: UUID, code: String) = ResponseEntity.ok(couponServicePort.apply(customerId, code))
    override fun getUsedCouponsByCustomerId(customerId: UUID) = ResponseEntity.ok(couponServicePort.getUsedCouponsByCustomerId(customerId))
}