package br.com.bidei.auction.domain.dto

import java.util.*
import javax.validation.constraints.NotNull

data class PayAuctionDto (
        val paymentMethodId: String
)