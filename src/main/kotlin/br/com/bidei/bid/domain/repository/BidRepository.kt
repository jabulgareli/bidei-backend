package br.com.bidei.bid.domain.repository

import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.bid.domain.model.Bid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

@Repository
interface BidRepository : JpaRepository<Bid, UUID>, PagingAndSortingRepository<Bid, UUID> {
    fun findByCustomerIdAndAuctionIdAndValueAndPriceOnBidAndCreatedDateGreaterThanEqual(customerId: UUID, auctionId: UUID,
                                                          value: BigDecimal, priceOnBid: BigDecimal, createdDate: Timestamp) : Optional<Bid>

    fun findByCustomerIdAndAuctionId(customerId: UUID, auctionId: UUID, pageable: Pageable): Page<Bid>
    fun findByAuctionId(auctionId: UUID, pageable: Pageable): Page<Bid>
    @Query("SELECT a FROM Auction a JOIN Bid b ON b.auction.id = a.id WHERE b.customer.id = :customerId GROUP BY a")
    fun findDistinctAuctionIdByCustomerId(customerId: UUID, pageable: Pageable): Page<Auction>
    fun countByCustomerId(customerId: UUID): Int
}