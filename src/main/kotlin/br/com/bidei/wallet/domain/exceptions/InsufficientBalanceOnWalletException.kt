package br.com.bidei.wallet.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.PAYMENT_REQUIRED, reason = "Insufficient balance on wallet")
class InsufficientBalanceOnWalletException : RuntimeException() {
}