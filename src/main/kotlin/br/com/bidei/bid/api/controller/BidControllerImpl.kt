package br.com.bidei.bid.api.controller

import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.application.ports.BidService
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.function.EntityResponse
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
class BidControllerImpl(private val bidService: BidService) : BidController{
    override fun create(@RequestHeader customerId: UUID,
                        @Valid @RequestBody newBidDto: NewBidDto): ResponseEntity<Void>{
        newBidDto.customerId = customerId
        val created = bidService.newBid(newBidDto)
        return ResponseEntity.created(URI("api/v1/bid/${created.id}")).build()
    }

    override fun getBidValuesByProductType(@PathVariable productType: AuctionProductType): ResponseEntity<List<BidValue>> {
        val result = bidService.listBidValues(productType)

        if (!result.isPresent)
            return ResponseEntity.notFound().build()

        return  ResponseEntity.ok(result.get())
    }

    override fun findByCustomerIdAndAuctionId(@PathVariable customerId: UUID,
                                              @PathVariable auctionId: UUID,
                                              @RequestParam(value = "page", defaultValue = "0") page: Int,
                                              @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                                              @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                                              @RequestParam(value = "perPage", defaultValue = "3") perPage: Int): ResponseEntity<Page<Bid>> {
        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(bidService.findByCustomerIdAndAuctionId(customerId, auctionId, pageRequest))
    }

    override fun findByAuctionId(@PathVariable auctionId: UUID,
                                 @RequestParam(value = "page", defaultValue = "0") page: Int,
                                 @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                                 @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                                 @RequestParam(value = "perPage", defaultValue = "3") perPage: Int): ResponseEntity<Page<Bid>> {
        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(bidService.findByAuctionId( auctionId, pageRequest))
    }

}