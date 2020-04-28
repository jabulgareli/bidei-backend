package br.com.bidei.integrations.vehicles.domain.ports.services

import br.com.bidei.integrations.vehicles.domain.dto.CarPriceResultDto

interface VehiclesService {
    fun brands(): String?
    fun models(brandId: String): String?
    fun versions(modelId: String, year: Int): String?
    fun colors(): String?
    fun equipments(vehicleId: Int): String?
    fun grades(): String?
    fun prices(vehicleId: Int, vehiclePriceTypeID: Int): List<CarPriceResultDto>
    fun fuelTypes(): String?
    fun transmissionTypes(): String?
}