package br.com.bidei.auction.application.dto

import br.com.bidei.address.domain.model.City
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AuctionDto(
        val id: UUID? = UUID.randomUUID(),
        val customerId: UUID?= UUID(0,0),
        val city: City,
        val endDate: Date? = null,
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
        val carConditions: Map<String, String>? = emptyMap(),
        val manuallyFinishedAt: Date? = null,
        val createdDate: Date? = null,
        val currentPrice: BigDecimal? = null){

}