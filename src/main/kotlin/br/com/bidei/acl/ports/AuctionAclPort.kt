package br.com.bidei.acl.ports

import br.com.bidei.auction.domain.model.Auction
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

interface AuctionAclPort {
    fun findById(id: UUID): Auction
    fun acceptBid(auction: Auction, bid: BigDecimal, currentPrice: BigDecimal)
}