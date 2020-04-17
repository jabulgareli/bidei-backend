package br.com.bidei.bid.api.controller

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.domain.dto.BidResponseDto
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


interface BidController {
    @PostMapping("api/v1/bid")
    fun create(@RequestHeader customerId: UUID,
               @Valid @RequestBody newBidDto: NewBidDto): ResponseEntity<Void>

    @GetMapping("/public/api/v1/bid/values/{productType}")
    fun getBidValuesByProductType(@PathVariable productType: AuctionProductType): ResponseEntity<List<BidValue>>

    @GetMapping("api/v1/bid/auction/{auctionId}/customer/{customerId}")
    fun findByCustomerIdAndAuctionId(@PathVariable customerId: UUID,
                                     @PathVariable auctionId: UUID,
                                     @RequestParam(value = "page", defaultValue = "0") page: Int,
                                     @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                                     @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                                     @RequestParam(value = "perPage", defaultValue = "3") perPage: Int): ResponseEntity<Page<BidResponseDto>>


    @GetMapping("api/v1/bid/customer/{customerId}/auctions")
    fun findAuctionsWithBidByCustomer(@PathVariable customerId: UUID,
                                      @RequestParam(value = "page", defaultValue = "0") page: Int,
                                      @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                                      @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                                      @RequestParam(value = "perPage", defaultValue = "3") perPage: Int): ResponseEntity<Page<AuctionDto>>

    @GetMapping("api/v1/bid/auction/{auctionId}")
    fun findByAuctionId(@PathVariable auctionId: UUID,
                        @RequestParam(value = "page", defaultValue = "0") page: Int,
                        @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                        @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                        @RequestParam(value = "perPage", defaultValue = "3") perPage: Int): ResponseEntity<Page<BidResponseDto>>
}