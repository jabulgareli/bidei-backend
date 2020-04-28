package br.com.bidei.integrations.vehicles.domain.dto

import br.com.bidei.utils.MathUtils
import java.math.BigDecimal

data class CarPriceResultDto(
    val VehicleGrade: Int,
    val VehiclePrices: VehiclePricesDto
)

data class VehiclePricesDto(
        val BaseDiscountedPrice: BigDecimal? = BigDecimal.ZERO,
        val EquipmentAdjustedPrice: BigDecimal = BigDecimal.ZERO,
        val FPP: BigDecimal = BigDecimal.ZERO,
        val ID: Int,
        val PriceHigh: BigDecimal = BigDecimal.ZERO,
        val PriceLow: BigDecimal = BigDecimal.ZERO,
        val PriceSuggestion: BigDecimal = MathUtils.round(PriceLow.divide(BigDecimal.valueOf(100)), 2)
                                                   .multiply(BigDecimal.valueOf(100))
)