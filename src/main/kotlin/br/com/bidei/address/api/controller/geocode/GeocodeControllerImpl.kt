package br.com.bidei.address.api.controller.geocode

import br.com.bidei.address.domain.ports.services.GeocodeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class GeocodeControllerImpl(private val geocodeService: GeocodeService) : GeocodeController {

    override fun geocode(@RequestParam latitude: BigDecimal, @RequestParam longitude: BigDecimal)
            = ResponseEntity.ok(geocodeService.findByLatitudeLongitude(latitude, longitude))

}