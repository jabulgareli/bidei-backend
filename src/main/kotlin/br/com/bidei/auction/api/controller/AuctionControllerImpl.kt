package br.com.bidei.auction.api.controller

import br.com.bidei.auction.application.dto.AuctionDto
import br.com.bidei.auction.application.dto.AuctionPhotoDto
import br.com.bidei.auction.application.dto.CreateOrUpdateAuctionDto
import br.com.bidei.auction.application.ports.AuctionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*
import javax.validation.Valid

@RestController
class AuctionControllerImpl(private val auctionService: AuctionService): AuctionController {
    override fun get(@RequestParam(value = "cityId", defaultValue = "0") cityId: Long,
                             @RequestParam(value = "stateId", defaultValue = "0") stateId: Long,
                             @RequestParam(value = "carBrand", defaultValue = "") brand: String,
                             @RequestParam(value = "carModel", defaultValue = "") model: String,
                             @RequestParam(value = "minFabricationYear", defaultValue = "0") minFabricationYear: Int,
                             @RequestParam(value = "maxKm", defaultValue = "-1") maxKm: Int,
                             @RequestParam(value = "page", defaultValue = "0") page: Int,
                             @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                             @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
                             @RequestParam(value = "perPage", defaultValue = "5") perPage: Int): ResponseEntity<Page<AuctionDto>> {

        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(auctionService.get(cityId, stateId, brand, model, minFabricationYear, maxKm, pageRequest))
    }

    override fun getById(@PathVariable auctionId: UUID) = auctionService.getAuctionDtoById(auctionId)

    override fun create(@RequestHeader("customerId")  customerId: UUID,
                        @Valid @RequestBody auctionDto: CreateOrUpdateAuctionDto): ResponseEntity<AuctionDto> {
        auctionDto.customerId = customerId

        val created = auctionService.create(auctionDto)
        return ResponseEntity.created(URI("public/api/v1/auctions/${created.id}")).body(created)
    }

    override fun update(@RequestHeader("customerId") customerId: UUID,
                        @Valid @RequestBody auctionDto: CreateOrUpdateAuctionDto,
                        @PathVariable auctionId: UUID): ResponseEntity<AuctionDto> {

        if (auctionDto.id != auctionId || auctionDto.id == UUID(0, 0))
            return ResponseEntity.badRequest().build()

        return ResponseEntity.ok(auctionService.update(customerId, auctionDto))
    }

    override fun addPhoto(@RequestHeader("customerId")  customerId: UUID,
                          @Valid @RequestBody auctionPhotoDto: AuctionPhotoDto, @PathVariable auctionId: UUID) =
            ResponseEntity.ok(auctionService.addPhoto(customerId, auctionId, auctionPhotoDto))

    override fun removePhoto(@RequestHeader("customerId")  customerId: UUID,
                             @PathVariable auctionId: UUID, @PathVariable photoName: String) =
            ResponseEntity.ok(auctionService.removePhoto(customerId, auctionId, photoName))

    override fun finish(@RequestHeader("customerId")  customerId: UUID,
                        @PathVariable auctionId: UUID): ResponseEntity<Void> {
        auctionService.finish(customerId, auctionId)
        return ResponseEntity.ok().build()
    }

    override fun getLatestAuctionsBy(@PathVariable customerId: UUID,
                                     @RequestParam(value = "onlyOpen", defaultValue = "true") onlyOpen: Boolean,
                                     @RequestParam(value = "page", defaultValue = "0") page: Int,
                                     @RequestParam(value = "perPage", defaultValue = "5") perPage: Int) =
            ResponseEntity.ok(auctionService.getByCustomerId(customerId,
                    onlyOpen,
                    PageRequest.of(page, perPage, Sort.by("endDate").descending())))

}