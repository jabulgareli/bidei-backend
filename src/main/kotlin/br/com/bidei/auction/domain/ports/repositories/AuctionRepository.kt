package br.com.bidei.auction.domain.ports.repositories

import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.cross.repository.EntityOwnerRepositoryBase
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface AuctionRepository : PagingAndSortingRepository<Auction, UUID>, JpaSpecificationExecutor<Auction>, EntityOwnerRepositoryBase<Auction, UUID> {
    @Modifying
    @Query("UPDATE Auction SET current_price = :newPrice where id = :auctionId and current_price = ROUND(:priceOnBid, 2)", nativeQuery = true)
    fun acceptBid(@Param("auctionId") auctionId: UUID,
                  @Param("newPrice") newPrice: BigDecimal,
                  @Param("priceOnBid") priceOnBid: BigDecimal): Int
}