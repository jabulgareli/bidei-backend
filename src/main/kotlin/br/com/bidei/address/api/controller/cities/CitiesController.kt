package br.com.bidei.address.api.controller.cities

import br.com.bidei.address.domain.model.City
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*

@RequestMapping("/public/api/v1/address")
interface CitiesController {

    @GetMapping("/cities")
    fun cities(state:String, city: String): ResponseEntity<MutableList<City>>

}