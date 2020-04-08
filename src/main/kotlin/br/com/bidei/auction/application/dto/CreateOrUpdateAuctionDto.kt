package br.com.bidei.auction.application.dto

import br.com.bidei.utils.DateUtils
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateOrUpdateAuctionDto(
        val id: UUID? = UUID.randomUUID(),
        var customerId: UUID?= UUID(0,0),
        @get:Min(message = "cityId is empty", value = 1)
        val cityId: Long? = 0,
        @get:NotNull(message = "endDate is empty")
        //        @get:FutureOrPresent
        val endDate: Date? = null,
        val photos: MutableList<String>? = arrayListOf(),
        @get:Min(message = "startPrice is empty", value = 1)
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
        @get:Min(message = "carKm is empty", value = 1)
        val carKm: Int = 0,
        val carOptions: List<String>? = emptyList(),
        @get:NotEmpty(message = "carTransmission is empty")
        val carTransmission: String? = null,
        val carConditions: MutableMap<String, String>? = mutableMapOf(),
        val manuallyFinishedAt: Date? = null,
        val createdDate: Date? = DateUtils.utcNow(),
        val currentPrice: BigDecimal? = startPrice)