package br.com.bidei.coupon.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Limit usage exceeded")
class LimitUsageExceededException : RuntimeException() {
}