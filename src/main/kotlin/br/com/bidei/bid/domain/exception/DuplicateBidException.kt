package br.com.bidei.bid.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Already bidded this value")
class DuplicateBidException : RuntimeException(){

}