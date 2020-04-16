package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

data class WalletTransactionDto(
        val id: UUID,
        val createdDate: Timestamp,
        val source: String,
        val operationDescription: String,
        val coupon: String,
        val transactionCode: String,
        val amount: BigDecimal
) {
    object Map {
        fun fromWalletStatement(walletStatement: WalletStatement) =
                WalletTransactionDto(walletStatement.id!!,
                        walletStatement.createdAt!!,
                        walletStatement.source,
                        walletStatement.operationDescription ?: "n/a",
                        walletStatement.coupon!!,
                        walletStatement.transactionCode,
                        walletStatement.amount ?: BigDecimal.ZERO)
    }
}