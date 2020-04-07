package br.com.bidei.bid.application.ports

import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface BidService {
    fun newBid(newBidDto: NewBidDto): Bid
    fun listBidValues(productType: AuctionProductType): Optional<List<BidValue>>
    fun findByCustomerIdAndAuctionId(customerId: UUID, auctionId: UUID, pageable: Pageable): Page<Bid>
    fun findByAuctionId(auctionId: UUID, pageable: Pageable): Page<Bid>
}