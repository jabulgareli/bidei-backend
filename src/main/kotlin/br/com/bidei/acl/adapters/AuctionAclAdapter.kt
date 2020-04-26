package br.com.bidei.acl.adapters

import br.com.bidei.auction.domain.model.Auction
import java.math.BigDecimal
import java.util.*
import br.com.bidei.acl.ports.AuctionAclPort
import br.com.bidei.auction.domain.ports.services.AuctionService
import org.springframework.stereotype.Service

@Service
class AuctionAclAdapter(
        private val auctionService: AuctionService) : AuctionAclPort {

    override fun findById(id: UUID) = auctionService.getById(id)

    override fun acceptBid(auction: Auction, bid: BigDecimal, currentPrice: BigDecimal) {
        auctionService.acceptBid(auction, bid, currentPrice)
    }
}