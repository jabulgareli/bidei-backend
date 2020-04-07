package br.com.bidei.wallet.domain.dto

import java.math.BigDecimal
import java.util.*

data class WalletBalanceDebitDto (
        val customerId: UUID,
        val bids: BigDecimal = BigDecimal.ONE,
        val source: WalletChargeEnum = WalletChargeEnum.BALANCE,
        val operationDescription: String = "Bid realizado"
)