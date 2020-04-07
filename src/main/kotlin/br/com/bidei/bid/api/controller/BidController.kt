package br.com.bidei.bid.api.controller

import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


interface BidController {
    @PostMapping("api/v1/bid")
    fun create(customerId: UUID, newBidDto: NewBidDto): ResponseEntity<Void>
    @GetMapping("/public/api/v1/bid/values/{productType}")
    fun getBidValuesByProductType(productType: AuctionProductType): ResponseEntity<List<BidValue>>
    @GetMapping("api/v1/bid/auction/{auctionId}/customer/{customerId}")
    fun findByCustomerIdAndAuctionId(customerId: UUID,
                                     auctionId: UUID,
                                     page: Int,
                                     sortBy: String,
                                     direction: String,
                                     perPage: Int): ResponseEntity<Page<Bid>>
    @GetMapping("api/v1/bid/auction/{auctionId}")
    fun findByAuctionId(auctionId: UUID,
                        page: Int,
                        sortBy: String,
                        direction: String,
                        perPage: Int): ResponseEntity<Page<Bid>>
}