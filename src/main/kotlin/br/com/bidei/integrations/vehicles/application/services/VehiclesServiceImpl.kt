package br.com.bidei.integrations.vehicles.application.services

import br.com.bidei.integrations.vehicles.infrastructure.ports.VehiclesApiKbbPort
import org.springframework.stereotype.Service

@Service
class VehiclesServiceImpl(private val vehiclesApiKbbPort: VehiclesApiKbbPort) : VehiclesService {

    override fun brands(): String? = vehiclesApiKbbPort.getAllVehicleBrand()

    override fun models(brandId: String): String? = vehiclesApiKbbPort.getAllVehicleModel(brandId)

    override fun versions(modelId: String, year: Int): String? = vehiclesApiKbbPort.getAllVehicleVersion(modelId, year)

    override fun colors(): String? = vehiclesApiKbbPort.getAllVehicleColor()

    override fun equipments(vehicleId: Int): String? = vehiclesApiKbbPort.getAllEquipments(vehicleId)

    override fun grades(): String? = vehiclesApiKbbPort.getAllVehicleGrade()

    override fun prices(vehicleId: Int, vehiclePriceTypeID: Int): String? = vehiclesApiKbbPort.getAllVehiclePriceType(vehicleId, vehiclePriceTypeID)

}