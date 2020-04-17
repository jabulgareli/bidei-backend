package br.com.bidei.auction.application.ports

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.dto.AuctionPhotoDto
import br.com.bidei.auction.domain.dto.CreateOrUpdateAuctionDto
import br.com.bidei.auction.domain.model.Auction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal
import java.util.*

interface AuctionService {
    fun create(auctionDto: CreateOrUpdateAuctionDto): AuctionDto

    fun update(customerId: UUID,
                       auctionDto: CreateOrUpdateAuctionDto): AuctionDto

    fun acceptBid(auction: Auction, bid: BigDecimal, currentPrice: BigDecimal)

    fun get(cityId: Long,
            stateId: Long,
            brand: String,
            model: String,
            minFabricationYear: Int,
            maxKm: Int,
            pageable: Pageable): Page<AuctionDto>

    fun getAuctionDtoById(id: UUID): AuctionDto
    fun getById(id: UUID): Auction

    fun getByCustomerId(customerId: UUID, onlyOpen: Boolean?, pageable: Pageable): Page<AuctionDto>

    fun finish(customerId: UUID,
               id: UUID): AuctionDto

    fun addPhoto(customerId: UUID,
                 id: UUID,
                 photoDto: AuctionPhotoDto): AuctionDto

    fun removePhoto(customerId: UUID,
                    id: UUID,
                    photoName: String): AuctionDto

    fun loadAuctionFromDto(auctionDto: CreateOrUpdateAuctionDto): Auction
    fun convertAuctionToCreateDto(auction: Auction): CreateOrUpdateAuctionDto
}