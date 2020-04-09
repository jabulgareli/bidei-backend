package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.domain.model.WalletStatement
import java.util.*

data class WalletTransactionDto(
        val id: UUID,
        val source: String,
        val message: String,
        val createdDate: Date
){
    object Map {
        fun fromWalletStatement(walletStatement: WalletStatement) =
                WalletTransactionDto(walletStatement.id!!,
                        walletStatement.source,
                        walletStatement.message,
                        walletStatement.createdAt!!)
    }
}