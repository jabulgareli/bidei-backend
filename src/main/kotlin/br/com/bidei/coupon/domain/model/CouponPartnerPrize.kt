package br.com.bidei.coupon.domain.model

import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
data class CouponPartnerPrize (
        @Id
        val id: UUID,
        @OneToOne
        val customer: Customer,
        @NotNull
        val prize: BigDecimal,
        @OneToOne
        val statement: WalletStatement,
        val createdAt: Timestamp = DateUtils.utcNow()
)