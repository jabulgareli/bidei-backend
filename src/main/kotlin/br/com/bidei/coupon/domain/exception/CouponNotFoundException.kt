package br.com.bidei.coupon.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Coupon not found")
class CouponNotFoundException : RuntimeException(){
}