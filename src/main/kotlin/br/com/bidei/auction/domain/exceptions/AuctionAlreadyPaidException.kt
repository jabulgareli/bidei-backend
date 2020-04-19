package br.com.bidei.auction.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED, reason = "Auction already paid")
class AuctionAlreadyPaidException : RuntimeException() {
}