package br.com.bidei.wallet.domain.model

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.exceptions.InsufficientBalanceOnWalletException
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class WalletCustomer(
        @Id
        val id: UUID? = UUID.randomUUID(),

        @OneToOne
        val customer: Customer,

        val referenceId: String, // External ID from payment partner (Iugu)

        val createdAt: Timestamp? = Timestamp(System.currentTimeMillis()),

        var bids: BigDecimal = BigDecimal.ZERO
) {
        fun chargeWallet(bids: BigDecimal) {
                if ((bids < BigDecimal.ZERO) && (this.bids.plus(bids) < BigDecimal.ZERO))
                        throw InsufficientBalanceOnWalletException()

                this.bids = this.bids.plus(bids)
        }
}