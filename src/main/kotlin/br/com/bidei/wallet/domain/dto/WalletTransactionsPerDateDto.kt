package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.domain.model.WalletStatement
import java.sql.Timestamp
import java.util.*

data class WalletTransactionsPerDateDto(
        val date: Timestamp,
        val transactions: List<WalletTransactionDto>
)