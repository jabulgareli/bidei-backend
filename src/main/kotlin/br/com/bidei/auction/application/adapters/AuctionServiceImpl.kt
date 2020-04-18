package br.com.bidei.auction.application.adapters

import br.com.bidei.acl.ports.AddressAclPort
import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.dto.AuctionPhotoDto
import br.com.bidei.auction.domain.exceptions.AuctionNotFoundException
import br.com.bidei.auction.domain.exceptions.CityNotFoundException
import br.com.bidei.auction.domain.exceptions.CustomerNotFoundException
import br.com.bidei.auction.application.ports.AuctionService
import br.com.bidei.auction.domain.exceptions.AuctionAlreadyFinishedException
import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.auction.domain.ports.repositories.AuctionRepository
import br.com.bidei.auction.domain.ports.services.FileUploadServicePort
import br.com.bidei.auction.domain.specifications.AuctionSpecifications
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.auction.domain.dto.CreateOrUpdateAuctionDto
import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.domain.exception.PriceChangedException
import br.com.bidei.cross.services.EntityOwnerServiceBase
import br.com.bidei.utils.DateUtils
import br.com.bidei.utils.jsonListOfAuctionCarOption
import br.com.bidei.utils.jsonListOfStringType
import br.com.bidei.utils.jsonMapOfStringType
import com.google.gson.Gson
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class AuctionServiceImpl(private val auctionRepository: AuctionRepository,
                         private val customerAcl: CustomersAclPort,
                         private val addressAcl: AddressAclPort,
                         private val gson: Gson,
                         private val fileUploadServicePort: FileUploadServicePort
) : AuctionService, EntityOwnerServiceBase<Auction, UUID>() {

    @Transactional
    override fun create(auctionDto: CreateOrUpdateAuctionDto): AuctionDto =
            AuctionDto.Map.fromAuction(gson, auctionRepository.save(loadAuctionFromDto(auctionDto)))

    @Transactional
    override fun update(customerId: UUID, auctionDto: CreateOrUpdateAuctionDto): AuctionDto {
        checkOwner(auctionDto.id!!, customerId)

        val auction = auctionRepository.findById(auctionDto.id)

        if(auctionDto.isRegisterFinished == true && auction.get().isRegisterFinished == false)
            auctionDto.updateEndDate()

        return AuctionDto.Map.fromAuction(gson, (auctionRepository.save(loadAuctionFromDto(auctionDto))))
    }

    @Transactional
    override fun acceptBid(auction: Auction, bid: BigDecimal, currentPrice: BigDecimal) {
        val updatedRecords = auctionRepository.acceptBid(auction.id!!, auction.currentPrice?.plus(bid)!!, currentPrice)

        auction.acceptBid(bid)

        if (updatedRecords == 0)
            throw PriceChangedException()
    }

    override fun get(cityId: Long,
                     stateId: Long,
                     brand: String,
                     model: String,
                     minFabricationYear: Int,
                     maxKm: Int,
                     pageable: Pageable): Page<AuctionDto> {

        val spec = AuctionSpecifications.isOpen()
                .and(AuctionSpecifications.isRegisterFinished())!!
                .and(AuctionSpecifications.withCarModel(model))!!
                .and(AuctionSpecifications.withMinFabricationYear(minFabricationYear))!!
                .and(AuctionSpecifications.withMaxKm(maxKm))!!
                .and(AuctionSpecifications.withCityId(cityId))!!
                .and(AuctionSpecifications.withCarBrand(brand))!!
                .and(AuctionSpecifications.withStateId(stateId))

        return auctionRepository.findAll(spec, pageable)
                .map{a -> AuctionDto.Map.fromAuction(gson, a)}
    }

    override fun getAuctionDtoById(id: UUID) =
            AuctionDto.Map.fromAuction(gson, auctionRepository.findById(id).orElseThrow { AuctionNotFoundException() })

    override fun getById(id: UUID): Auction =
            auctionRepository.findById(id).orElseThrow { AuctionNotFoundException() }

    @Transactional
    override fun finish(customerId: UUID,
                        id: UUID): AuctionDto {
        checkOwner(id, customerId)

        val auction = getById(id)

        if (auction.isFinished())
            throw AuctionAlreadyFinishedException()

        auction.finish()
        return AuctionDto.Map.fromAuction(gson, auctionRepository.save(auction))
    }

    @Transactional
    override fun addPhoto(customerId: UUID,
                          id: UUID,
                          photoDto: AuctionPhotoDto): AuctionDto {
        checkOwner(id, customerId)

        val auction = getById(id)
        var photos = gson.fromJson<MutableList<String>>(auction.photos, jsonListOfStringType)
        val photoName = UUID.randomUUID().toString()
        val photoPath = getPathPhoto(id, photoName)

        val uploadedUrl = fileUploadServicePort.uploadImage(photoDto.image!!, photoPath)

        if (photos == null)
            photos = mutableListOf()

        photos.add(uploadedUrl)

        auction.photos = gson.toJson(photos)

        return update(customerId, convertAuctionToCreateDto(auction))
    }

    @Transactional
    override fun removePhoto(customerId: UUID,
                             id: UUID,
                             photoName: String): AuctionDto {
        checkOwner(id, customerId)

        val auction = getById(id)
        val photos = gson.fromJson<MutableList<String>>(auction.photos, jsonListOfStringType)
        val imagePath = getPathPhoto(id, photoName)


        fileUploadServicePort.deletePhoto(imagePath)
        photos?.removeIf { it.contains(photoName) }

        auction.photos = gson.toJson(photos)

        return update(customerId, convertAuctionToCreateDto(auction))
    }

    override fun loadAuctionFromDto(auctionDto: CreateOrUpdateAuctionDto): Auction {
        val customer = customerAcl.findById(auctionDto.customerId!!).orElse(null)
                ?: throw CustomerNotFoundException()

//        val city = addressAcl.findCityById(auctionDto.cityId!!).orElse(null)
//                ?: throw CityNotFoundException()

        return Auction(
                auctionDto.id ?: UUID.randomUUID(),
                customer,
                //city,
                customer.city,
                auctionDto.endDate!!,
                auctionDto.startPrice!!,
                auctionDto.carBrand!!,
                auctionDto.carModel!!,
                auctionDto.carVersion!!,
                auctionDto.carFabricationYear,
                auctionDto.carModelYear,
                auctionDto.carFuelType!!,
                auctionDto.carKm,
                gson.toJson(auctionDto.carOptions),
                auctionDto.carTransmission!!,
                gson.toJson(auctionDto.carConditions),
                auctionDto.manuallyFinishedAt,
                auctionDto.createdDate,
                auctionDto.currentPrice,
                AuctionProductType.CAR,
                gson.toJson(auctionDto.carCharacteristics),
                auctionDto.carIsArmored,
                carColor = auctionDto.carColor,
                isRegisterFinished = auctionDto.isRegisterFinished)
    }

    override fun getByCustomerId(customerId: UUID, onlyOpen: Boolean?, pageable: Pageable): Page<AuctionDto> {
        var spec: Specification<Auction>? = AuctionSpecifications.withCustomerId(customerId)

        if (onlyOpen != null) {
            spec = if (onlyOpen)
                spec?.and(AuctionSpecifications.isOpen())
            else
                spec?.and(AuctionSpecifications.isExpired())?.or(AuctionSpecifications.isManuallyFinished())
        }

        return auctionRepository.findAll(spec, pageable)
                .map{a -> AuctionDto.Map.fromAuction(gson, a)}
    }

    override fun convertAuctionToCreateDto(auction: Auction) =
            CreateOrUpdateAuctionDto(auction.id,
                    auction.customer.id,
                    auction.city.id,
                    auction.endDate,
                    auction.startPrice,
                    auction.carBrand,
                    auction.carModel,
                    auction.carVersion,
                    auction.carFabricationYear,
                    auction.carModelYear,
                    auction.carFuelType,
                    auction.carKm,
                    gson.fromJson(auction.carOptions, jsonListOfStringType),
                    auction.carTransmission,
                    gson.fromJson(auction.carConditions, jsonListOfAuctionCarOption),
                    auction.manuallyFinishedAt,
                    auction.createdDate,
                    auction.currentPrice,
                    AuctionProductType.CAR,
                    gson.fromJson(auction.carCharacteristics, jsonListOfStringType),
                    auction.carIsArmored,
                    carColor = auction.carColor,
                    isRegisterFinished = auction.isRegisterFinished)

    private fun getPathPhoto(id: UUID, name: String) =
            "auctions/${id}/" + name.replace(".png", "") + ".png"


}