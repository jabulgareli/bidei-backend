package br.com.bidei.customers.application.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "City Not Found")
class CityNotFoundException : RuntimeException()
