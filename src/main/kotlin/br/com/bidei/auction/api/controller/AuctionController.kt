package br.com.bidei.auction.api.controller

import br.com.bidei.auction.application.dto.AuctionDto
import br.com.bidei.auction.application.dto.AuctionPhotoDto
import br.com.bidei.auction.application.dto.CreateOrUpdateAuctionDto
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

private const val PUBLIC_PATH = "public/api/v1/auctions"
private const val PRIVATE = "api/v1/auctions"

interface AuctionController {
    @GetMapping(PUBLIC_PATH)
    fun get(cityId: Long,
                    stateId: Long,
                    brand: String,
                    model: String,
                    minFabricationYear: Int,
                    maxKm: Int,
                    page: Int,
                    sortBy: String,
                    direction: String,
                    perPage: Int): ResponseEntity<Page<AuctionDto>>

    @GetMapping("$PUBLIC_PATH/{auctionId}")
    fun getById(auctionId: UUID): AuctionDto

    @GetMapping("$PRIVATE/customers/{customerId}")
    fun getLatestAuctionsBy(customerId: UUID,
                            onlyOpen: Boolean,
                            page: Int,
                            perPage: Int): ResponseEntity<Page<AuctionDto>>

    @PostMapping(PRIVATE)
    fun create(customerId: UUID,
               auctionDto: CreateOrUpdateAuctionDto): ResponseEntity<AuctionDto>

    @PutMapping("$PRIVATE/{auctionId}")
    fun update(customerId: UUID,
               auctionDto: CreateOrUpdateAuctionDto,
               auctionId: UUID): ResponseEntity<AuctionDto>

    @PutMapping("$PRIVATE/{auctionId}/finish")
    fun finish(customerId: UUID, auctionId: UUID): ResponseEntity<Void>

    @PostMapping("$PRIVATE/{auctionId}/photos")
    fun addPhoto(customerId: UUID,
                 auctionPhotoDto: AuctionPhotoDto,
                 auctionId: UUID): ResponseEntity<AuctionDto>

    @DeleteMapping("$PRIVATE/{auctionId}/photos/{photoName}")
    fun removePhoto(customerId: UUID,
                    auctionId: UUID,
                    photoName: String): ResponseEntity<AuctionDto>

}