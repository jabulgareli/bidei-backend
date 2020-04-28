package br.com.bidei.integrations.vehicles.domain.ports.services

interface VehiclesApiKbbPort {
    fun getAllVehicleBrand(): String?
    fun getAllVehicleModel(brandId: String): String?
    fun getAllVehicleVersion(modelId: String, year: Int): String?
    fun getAllVehicleColor(): String?
    fun getAllEquipments(vehicleId: Int): String?
    fun getAllVehicleGrade(): String?
    fun getAllVehiclePriceType(vehicleId: Int, vehiclePriceTypeID: Int): String?
    fun getAllTransmissionTypes(): String?
    fun getAllFuelTypes(): String?
}