package br.com.bidei.customers.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Customer already registered")
class CustomerAlreadyRegisteredException : RuntimeException() {
}