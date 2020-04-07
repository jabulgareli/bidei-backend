package br.com.bidei.auction.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Auction already finished")
class AuctionAlreadyFinishedException : RuntimeException() {
}