package br.com.bidei.factories

import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.customers.domain.model.Customer
import java.math.BigDecimal
import java.util.*

object BidFactory {
    val defaultBid = BigDecimal.valueOf(100)

    fun newValidBidRequest() = NewBidDto(
            CustomerFactory.customerId,
            AuctionFactory.validAuctionId,
            defaultBid,
            AuctionFactory.defaultCarStartPrice
    )
}