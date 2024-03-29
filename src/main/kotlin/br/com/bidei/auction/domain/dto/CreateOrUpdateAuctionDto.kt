package br.com.bidei.auction.domain.dto

import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.utils.DateUtils
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import javax.validation.constraints.FutureOrPresent
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateOrUpdateAuctionDto(
        val id: UUID? = UUID.randomUUID(),
        var customerId: UUID?= UUID(0,0),
        val cityId: Long? = 0,
        var endDate: Timestamp? = DateUtils.utcNow(),
        @get:Min(message = "startPrice is empty", value = 0)
        val startPrice: BigDecimal? = null,
        @get:NotEmpty(message = "carBrand is empty")
        val carBrand: String? = null,
        @get:NotEmpty(message = "carModel is empty")
        val carModel: String? = null,
        @get:NotEmpty(message = "carVersion is empty")
        val carVersion: String? = null,
        @get:Min(message = "carFabricationYear is empty", value = 1)
        val carFabricationYear: Int = 0,
        @get:Min(message = "carModelYear is empty", value = 1)
        val carModelYear: Int = 0,
        @get:NotEmpty(message = "carFuelType is empty")
        val carFuelType: String? = null,
        @get:Min(message = "carKm is empty", value = 0)
        val carKm: Int = 0,
        val carOptions: List<String>? = emptyList(),
        @get:NotEmpty(message = "carTransmission is empty")
        val carTransmission: String? = null,
        val carConditions: ArrayList<AuctionCarOptionDto> = arrayListOf(),
        val manuallyFinishedAt: Timestamp? = null,
        val createdDate: Timestamp? = DateUtils.utcNow(),
        val currentPrice: BigDecimal? = startPrice,
        val productType: AuctionProductType? = AuctionProductType.CAR,
        val carCharacteristics: List<String>? = emptyList(),
        val carIsArmored: Boolean? = false,
        val carColor: String? = null,
        val isRegisterFinished: Boolean? = false) {

        fun updateEndDate(){
                endDate = DateUtils.addDays(DateUtils.utcNow(), 1)
        }
}