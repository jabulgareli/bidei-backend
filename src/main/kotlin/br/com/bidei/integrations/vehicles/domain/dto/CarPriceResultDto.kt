package br.com.bidei.integrations.vehicles.domain.dto

import br.com.bidei.utils.MathUtils
import java.math.BigDecimal

data class CarPriceResultDto(
    val VehicleGrade: Int,
    val VehiclePrices: VehiclePricesDto
)

data class VehiclePricesDto(
        val PaseDiscountedPrice: BigDecimal? = BigDecimal.ZERO,
        val EquipmentAdjustedPrice: BigDecimal? = BigDecimal.ZERO,
        val FPP: BigDecimal? = BigDecimal.ZERO,
        val Id: Int,
        val PriceHigh: BigDecimal? = BigDecimal.ZERO,
        val PriceLow: BigDecimal? = BigDecimal.ZERO,
        var PriceSuggestion: BigDecimal? = BigDecimal.ZERO
){
    fun calcPriceSuggestion(){
        PriceSuggestion = MathUtils.round(PriceLow!!.multiply(BigDecimal.valueOf(0.9))
                                                    .divide(BigDecimal.valueOf(1000)), 2)
                                   .multiply(BigDecimal.valueOf(1000))
    }
}