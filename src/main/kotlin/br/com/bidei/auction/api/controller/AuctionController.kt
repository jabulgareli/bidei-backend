package br.com.bidei.auction.api.controller

import br.com.bidei.auction.application.dto.AuctionDto
import br.com.bidei.auction.application.dto.AuctionPhotoDto
import br.com.bidei.auction.application.dto.CreateOrUpdateAuctionDto
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

private const val PUBLIC_PATH = "public/api/v1/auctions"
private const val PRIVATE = "api/v1/auctions"

interface AuctionController {
    @GetMapping(PUBLIC_PATH)
    fun get(@RequestParam(value = "cityId", defaultValue = "0") cityId: Long,
            @RequestParam(value = "stateId", defaultValue = "0") stateId: Long,
            @RequestParam(value = "carBrand", defaultValue = "") brand: String,
            @RequestParam(value = "carModel", defaultValue = "") model: String,
            @RequestParam(value = "minFabricationYear", defaultValue = "0") minFabricationYear: Int,
            @RequestParam(value = "maxKm", defaultValue = "-1") maxKm: Int,
            @RequestParam(value = "page", defaultValue = "0") page: Int,
            @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
            @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
            @RequestParam(value = "perPage", defaultValue = "5") perPage: Int): ResponseEntity<Page<AuctionDto>>

    @GetMapping("$PUBLIC_PATH/{auctionId}")
    fun getById(@PathVariable auctionId: UUID): AuctionDto

    @GetMapping("$PRIVATE/customers/{customerId}")
    fun getLatestAuctionsBy(@PathVariable customerId: UUID,
                            @RequestParam(value = "onlyOpen", defaultValue = "true") onlyOpen: Boolean,
                            @RequestParam(value = "page", defaultValue = "0") page: Int,
                            @RequestParam(value = "perPage", defaultValue = "5") perPage: Int): ResponseEntity<Page<AuctionDto>>

    @PostMapping(PRIVATE)
    fun create(@RequestHeader("customerId") customerId: UUID,
               @Valid @RequestBody auctionDto: CreateOrUpdateAuctionDto): ResponseEntity<AuctionDto>

    @PutMapping("$PRIVATE/{auctionId}")
    fun update(@RequestHeader("customerId") customerId: UUID,
               @Valid @RequestBody auctionDto: CreateOrUpdateAuctionDto,
               @PathVariable auctionId: UUID): ResponseEntity<AuctionDto>

    @PutMapping("$PRIVATE/{auctionId}/finish")
    fun finish(@RequestHeader("customerId")customerId: UUID,
               @PathVariable auctionId: UUID): ResponseEntity<Void>

    @PostMapping("$PRIVATE/{auctionId}/photos")
    fun addPhoto(@RequestHeader("customerId") customerId: UUID,
                 @Valid @RequestBody auctionPhotoDto: AuctionPhotoDto,
                 @PathVariable auctionId: UUID): ResponseEntity<AuctionDto>

    @DeleteMapping("$PRIVATE/{auctionId}/photos/{photoName}")
    fun removePhoto(@RequestHeader("customerId") customerId: UUID,
                    @PathVariable auctionId: UUID,
                    @PathVariable photoName: String): ResponseEntity<AuctionDto>

}