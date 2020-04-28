package br.com.bidei.factories

import br.com.bidei.bid.domain.dto.NewBidDto
import java.math.BigDecimal

object BidFactory {
    val defaultBid = BigDecimal.valueOf(100)

    fun newValidBidRequest(bidValue: BigDecimal? = null) = NewBidDto(
            CustomerFactory.customerId,
            AuctionFactory.validAuctionId,
            bidValue ?: defaultBid,
            AuctionFactory.defaultCarStartPrice
    )
}