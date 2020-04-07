package br.com.bidei.integrations.vehicles.application.adapters

import br.com.bidei.integrations.vehicles.application.ports.VehiclesController
import br.com.bidei.integrations.vehicles.application.services.VehiclesService
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

    override fun prices(vehicleId: Int, vehiclePriceTypeID: Int): ResponseEntity<String> =
            ResponseEntity.ok(vehiclesService.prices(vehicleId, vehiclePriceTypeID).toString())

}