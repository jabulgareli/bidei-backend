package br.com.bidei.cross.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Entity not owned by customer")
class EntityNotOwnedByCustomer : RuntimeException() {
}