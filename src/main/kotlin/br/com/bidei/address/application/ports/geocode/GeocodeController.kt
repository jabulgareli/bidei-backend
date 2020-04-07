package br.com.bidei.address.application.ports.geocode

import br.com.bidei.address.domain.dto.GeocodeDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal

@RequestMapping("/public/api/v1/geocode")
interface GeocodeController {

    @GetMapping()
    fun geocode(latitude: BigDecimal, longitude: BigDecimal): ResponseEntity<GeocodeDto>

}