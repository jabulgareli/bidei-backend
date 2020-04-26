package br.com.bidei.customers.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer Not Found")
class CustomerNotFoundException : RuntimeException()
