package br.com.bidei.auction.api.controller

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.dto.AuctionPhotoDto
import br.com.bidei.auction.domain.dto.CreateOrUpdateAuctionDto
import br.com.bidei.auction.application.ports.AuctionService
import br.com.bidei.auction.domain.dto.PayAuctionDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
class AuctionControllerImpl(private val auctionService: AuctionService) : AuctionController {
    override fun get(cityId: Long,
                     stateId: Long,
                     brand: String,
                     model: String,
                     minFabricationYear: Int,
                     maxKm: Int,
                     page: Int,
                     sortBy: String,
                     direction: String,
                     perPage: Int): ResponseEntity<Page<AuctionDto>> {

        var pageRequest = if (direction.toUpperCase() == "DESC") {
            PageRequest.of(page, perPage, Sort.by(sortBy).descending())
        } else {
            PageRequest.of(page, perPage, Sort.by(sortBy).ascending())
        }

        return ResponseEntity.ok(auctionService.get(cityId, stateId, brand, model, minFabricationYear, maxKm, pageRequest))
    }

    override fun getById(auctionId: UUID) = auctionService.getAuctionDtoById(auctionId)

    override fun getLatestAuctionsBy(customerId: UUID,
                                     onlyOpen: Boolean?,
                                     page: Int,
                                     perPage: Int) =
            ResponseEntity.ok(auctionService.getByCustomerId(customerId,
                    onlyOpen,
                    PageRequest.of(page, perPage, Sort.by("endDate").descending())))

    override fun create(customerId: UUID,
                        auctionDto: CreateOrUpdateAuctionDto): ResponseEntity<AuctionDto> {
        auctionDto.customerId = customerId

        val created = auctionService.create(auctionDto)
        return ResponseEntity.created(URI("public/api/v1/auctions/${created.id}")).body(created)
    }

    override fun update(customerId: UUID,
                        auctionDto: CreateOrUpdateAuctionDto,
                        auctionId: UUID): ResponseEntity<AuctionDto> {

        if (auctionDto.id != auctionId || auctionDto.id == UUID(0, 0))
            return ResponseEntity.badRequest().build()

        return ResponseEntity.ok(auctionService.update(customerId, auctionDto))
    }


    override fun finish(customerId: UUID,
                        auctionId: UUID): ResponseEntity<Void> {
        auctionService.finish(customerId, auctionId)
        return ResponseEntity.ok().build()
    }

    override fun addPhoto(customerId: UUID,
                          auctionPhotoDto: AuctionPhotoDto,
                          auctionId: UUID) =
            ResponseEntity.ok(auctionService.addPhoto(customerId, auctionId, auctionPhotoDto))

    override fun payAuction(customerId: UUID,
                            auctionId: UUID,
                            payAuctionDto: PayAuctionDto): ResponseEntity<Unit> =
            ResponseEntity.ok(auctionService.payAuction(customerId, auctionId, payAuctionDto))

    override fun removePhoto(customerId: UUID,
                             auctionId: UUID,
                             photoName: String) =
            ResponseEntity.ok(auctionService.removePhoto(customerId, auctionId, photoName))
}