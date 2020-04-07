package br.com.bidei.bid.domain.repository

import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BidValueRepository : JpaRepository<BidValue, UUID> {
    fun findAllByProductType(productType: AuctionProductType): Optional<List<BidValue>>
}