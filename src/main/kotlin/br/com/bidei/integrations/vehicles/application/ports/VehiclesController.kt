package br.com.bidei.integrations.vehicles.application.ports

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping("/public/api/v1/vehicles")
interface VehiclesController {

    @GetMapping("/brands")
    fun brands(): ResponseEntity<String>

    @GetMapping("/models")
    fun models(brandId: String): ResponseEntity<String>

    @GetMapping("/versions")
    fun versions(modelId: String, year: Int): ResponseEntity<String>

    @GetMapping("/colors")
    fun versions(): ResponseEntity<String>

    @GetMapping("/equipments")
    fun equipments(vehicleId: Int): ResponseEntity<String>

    @GetMapping("/grades")
    fun grades(): ResponseEntity<String>

    @GetMapping("/prices")
    fun prices(vehicleId: Int, vehiclePriceTypeID: Int): ResponseEntity<String>

    @GetMapping("/fuel-types")
    fun fuelTypes(): ResponseEntity<String>

    @GetMapping("/transmission-types")
    fun transmissionTypes(): ResponseEntity<String>

}