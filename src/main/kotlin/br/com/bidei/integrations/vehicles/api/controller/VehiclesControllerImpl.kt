package br.com.bidei.integrations.vehicles.api.controller

import br.com.bidei.integrations.vehicles.domain.dto.CarPriceResultDto
import br.com.bidei.integrations.vehicles.domain.dto.VehiclePricesDto
import br.com.bidei.integrations.vehicles.domain.ports.services.VehiclesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class VehiclesControllerImpl(private val vehiclesService: VehiclesService) : VehiclesController {

    override fun brands(): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.brands().toString())

    override fun models(@RequestParam brandId: String): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.models(brandId).toString())

    override fun versions(@RequestParam modelId: String, @RequestParam year: Int): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.versions(modelId, year).toString())

    override fun versions(): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.colors().toString())

    override fun equipments(@RequestParam vehicleId: Int): ResponseEntity<String>
            = ResponseEntity.ok(vehiclesService.equipments(vehicleId).toString())

    override fun grades(): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.grades().toString())

    override fun prices(vehicleId: Int, vehiclePriceTypeID: Int): ResponseEntity<List<CarPriceResultDto>> =
            ResponseEntity.ok(vehiclesService.prices(vehicleId, vehiclePriceTypeID))

    override fun fuelTypes(): ResponseEntity<String> = ResponseEntity.ok(vehiclesService.fuelTypes().toString())

    override fun transmissionTypes(): ResponseEntity<String> = ResponseEntity.ok(vehiclesService.transmissionTypes().toString())

}