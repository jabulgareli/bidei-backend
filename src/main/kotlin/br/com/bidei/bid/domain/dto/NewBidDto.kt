package br.com.bidei.bid.domain.dto

import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class NewBidDto (
    var customerId: UUID? = null,
    val auctionId: UUID? = null,
    @get:Min(value = 1)
    val value: BigDecimal? = BigDecimal.ZERO,
    @get:Min(value = 1)
    val currentPrice: BigDecimal? = BigDecimal.ZERO
)