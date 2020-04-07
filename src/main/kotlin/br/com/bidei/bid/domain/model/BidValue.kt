package br.com.bidei.bid.domain.model

import br.com.bidei.auction.domain.model.AuctionProductType
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
data class BidValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Enumerated(EnumType.ORDINAL)
    val productType: AuctionProductType,
    val value: BigDecimal
)