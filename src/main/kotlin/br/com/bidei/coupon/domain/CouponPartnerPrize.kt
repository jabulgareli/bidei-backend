package br.com.bidei.coupon.domain

import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import java.math.BigDecimal
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
        val partner: Customer,
        @OneToOne
        val bid: Bid,
        @NotNull
        val prize: BigDecimal,
        val createdAt: Date = DateUtils.utcNow()
)