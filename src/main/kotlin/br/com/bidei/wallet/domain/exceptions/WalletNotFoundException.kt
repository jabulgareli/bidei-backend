package br.com.bidei.wallet.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Wallet not found")
class WalletNotFoundException : RuntimeException() {
}