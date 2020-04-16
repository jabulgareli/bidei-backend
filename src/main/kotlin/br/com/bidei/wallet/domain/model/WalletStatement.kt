package br.com.bidei.wallet.domain.model

import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class WalletStatement(
        @Id
        val id: UUID? = UUID.randomUUID(),

        @OneToOne
        val walletCustomer: WalletCustomer,

        val createdAt: Timestamp? = Timestamp(System.currentTimeMillis()),

        val source: String,

        val success: Boolean,

        val message: String,

        val url: String,

        val pdf: String,

        val invoiceId: String,

        val transactionCode: String,

        val bids: BigDecimal,

        val coupon: String? = "",

        val operationDescription: String? = "n/a",

        val amount: BigDecimal? = BigDecimal.ZERO
)