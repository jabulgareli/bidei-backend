package br.com.bidei.address.api.controller.cities

import br.com.bidei.address.domain.ports.services.CitiesService
import br.com.bidei.address.domain.model.City
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CitiesControllerImpl(private val citiesService: CitiesService) : CitiesController {

    override fun cities(@RequestParam state: String, @RequestParam city: String): ResponseEntity<MutableList<City>> =
            ResponseEntity.ok(citiesService.findByStateEqualsAndNameContains(state, city))

}