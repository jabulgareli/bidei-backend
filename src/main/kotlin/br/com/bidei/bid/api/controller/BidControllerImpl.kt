package br.com.bidei.bid.api.controller

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.domain.dto.NewBidDto
import br.com.bidei.bid.domain.ports.services.BidService
import br.com.bidei.bid.domain.dto.BidResponseDto
import br.com.bidei.bid.domain.model.BidValue
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
class BidControllerImpl(private val bidService: BidService) : BidController {
    override fun create(customerId: UUID,
                        newBidDto: NewBidDto): ResponseEntity<Void> {
        newBidDto.customerId = customerId
        val created = bidService.newBid(newBidDto)
        return ResponseEntity.created(URI("api/v1/bid/${created.id}")).build()
    }

    override fun getBidValuesByProductType(productType: AuctionProductType): ResponseEntity<List<BidValue>> {
        val result = bidService.listBidValues(productType)

        if (!result.isPresent)
            return ResponseEntity.notFound().build()

        return ResponseEntity.ok(result.get())
    }

    override fun findByCustomerIdAndAuctionId(customerId: UUID,
                                              auctionId: UUID,
                                              page: Int,
                                              sortBy: String,
                                              direction: String,
                                              perPage: Int): ResponseEntity<Page<BidResponseDto>> {

        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(bidService.findByCustomerIdAndAuctionId(customerId, auctionId, pageRequest))
    }

    override fun findAuctionsWithBidByCustomer(customerId: UUID,
                                               isOpen: Boolean?,
                                               page: Int,
                                               sortBy: String,
                                               direction: String,
                                               perPage: Int): ResponseEntity<Page<AuctionDto>> {

        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(bidService.findAuctionsWithBidByCustomer(customerId, isOpen, pageRequest))
    }

    override fun findByAuctionId(auctionId: UUID,
                                 page: Int,
                                 sortBy: String,
                                 direction: String,
                                 perPage: Int): ResponseEntity<Page<BidResponseDto>> {

        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(bidService.findByAuctionId(auctionId, pageRequest))
    }

}