package br.com.bidei.integrations.vehicles.application.services

interface VehiclesService {
    fun brands(): String?
    fun models(brandId: String): String?
    fun versions(modelId: String, year: Int): String?
    fun colors(): String?
    fun equipments(vehicleId: Int): String?
    fun grades(): String?
    fun prices(vehicleId: Int, vehiclePriceTypeID: Int): String?
}