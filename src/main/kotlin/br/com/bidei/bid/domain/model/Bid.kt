package br.com.bidei.bid.domain.model

import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.customers.domain.model.Customer
import java.math.BigDecimal
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class Bid (
        @Id
        val id: UUID? = UUID.randomUUID(),
        @OneToOne
        val auction: Auction,
        @OneToOne
        val customer: Customer,
        val value: BigDecimal,
        val priceOnBid: BigDecimal,
        val createdDate: Date
)