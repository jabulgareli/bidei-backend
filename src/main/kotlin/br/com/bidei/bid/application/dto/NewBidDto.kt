package br.com.bidei.bid.application.dto

import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

data class NewBidDto (
    var customerId: UUID? = UUID.randomUUID(),
    val auctionId: UUID? = null,
    @get:Min(value = 1)
    val value: BigDecimal? = BigDecimal.ZERO,
    @get:Min(value = 1)
    val currentPrice: BigDecimal? = BigDecimal.ZERO
)