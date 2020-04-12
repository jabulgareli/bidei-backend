package br.com.bidei.bid.domain.dto

import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.customers.domain.model.Customer
import java.math.BigDecimal
import java.util.*

data class BidResponseDto(val id: UUID? = UUID.randomUUID(),
                          val customer: Customer?,
                          val value: BigDecimal,
                          val priceOnBid: BigDecimal,
                          val createdDate: Date){
    object Map {
        fun fromBid(bid: Bid) =
                BidResponseDto(
                        bid.id,
                        if (bid.auction.isPaid!!) bid.customer else null,
                        bid.value,
                        bid.priceOnBid,
                        bid.createdDate
                )
    }
}