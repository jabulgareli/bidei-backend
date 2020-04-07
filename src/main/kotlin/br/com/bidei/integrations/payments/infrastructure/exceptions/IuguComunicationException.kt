package br.com.bidei.integrations.payments.infrastructure.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class IuguComunicationException : RuntimeException()