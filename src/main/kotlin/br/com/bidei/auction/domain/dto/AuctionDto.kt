package br.com.bidei.auction.domain.dto

import br.com.bidei.address.domain.model.City
import br.com.bidei.auction.domain.model.AuctionProductType
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

data class AuctionDto(
        val id: UUID? = UUID.randomUUID(),
        val customerId: UUID?= UUID(0,0),
        val city: City,
        val endDate: Timestamp? = null,
        val photos: MutableList<String>? = arrayListOf(),
        val startPrice: BigDecimal? = null,
        val carBrand: String? = null,
        val carModel: String? = null,
        val carVersion: String? = null,
        val carFabricationYear: Int = 0,
        val carModelYear: Int = 0,
        val carFuelType: String? = null,
        val carKm: Int = 0,
        val carOptions: List<String>? = emptyList(),
        val carTransmission: String? = null,
        val carConditions: ArrayList<AuctionCarOptionDto> = arrayListOf(),
        val manuallyFinishedAt: Timestamp? = null,
        val createdDate: Timestamp? = null,
        val currentPrice: BigDecimal? = null,
        val productType: AuctionProductType? = AuctionProductType.CAR,
        val carCharacteristics: List<String>? = emptyList(),
        val carIsArmored: Boolean? = false,
        val isPaid: Boolean,
        val isFinished: Boolean,
        val carColor: String? = null){

}