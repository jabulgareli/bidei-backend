package br.com.bidei.address.application.services.geocode

import br.com.bidei.address.domain.dto.GeocodeDto
import java.math.BigDecimal

interface GeocodeService {
    fun findByLatitudeLongitude(latitude: BigDecimal, longitude: BigDecimal): GeocodeDto
}