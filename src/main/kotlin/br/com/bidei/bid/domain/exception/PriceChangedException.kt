package br.com.bidei.bid.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Price has changed")
class PriceChangedException : RuntimeException() {
}