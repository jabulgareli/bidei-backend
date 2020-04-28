package br.com.bidei.integrations.vehicles.application.adapters

import br.com.bidei.integrations.vehicles.domain.dto.CarPriceResultDto
import br.com.bidei.integrations.vehicles.domain.dto.VehiclePricesDto
import br.com.bidei.integrations.vehicles.domain.ports.services.VehiclesService
import br.com.bidei.integrations.vehicles.domain.ports.services.VehiclesApiKbbPort
import br.com.bidei.utils.jsonListOfVehiclePricesDto
import com.google.gson.Gson
import org.springframework.stereotype.Service

@Service
class VehiclesServiceImpl(private val vehiclesApiKbbPort: VehiclesApiKbbPort,
                          private val gson: Gson) : VehiclesService {

    override fun brands(): String? = vehiclesApiKbbPort.getAllVehicleBrand()

    override fun models(brandId: String): String? = vehiclesApiKbbPort.getAllVehicleModel(brandId)

    override fun versions(modelId: String, year: Int): String? = vehiclesApiKbbPort.getAllVehicleVersion(modelId, year)

    override fun colors(): String? = vehiclesApiKbbPort.getAllVehicleColor()

    override fun equipments(vehicleId: Int): String? = vehiclesApiKbbPort.getAllEquipments(vehicleId)

    override fun grades(): String? = vehiclesApiKbbPort.getAllVehicleGrade()

    override fun prices(vehicleId: Int, vehiclePriceTypeID: Int): List<CarPriceResultDto> {
        val result = gson.fromJson<List<CarPriceResultDto>>(vehiclesApiKbbPort.getAllVehiclePriceType(vehicleId, vehiclePriceTypeID), jsonListOfVehiclePricesDto)
        result.forEach { p -> p.VehiclePrices.calcPriceSuggestion() }
        return result
    }


    override fun fuelTypes(): String? = vehiclesApiKbbPort.getAllFuelTypes()

    override fun transmissionTypes(): String? = vehiclesApiKbbPort.getAllTransmissionTypes()

}