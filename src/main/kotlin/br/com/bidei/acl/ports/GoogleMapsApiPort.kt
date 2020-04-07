package br.com.bidei.acl.ports

import br.com.bidei.acl.dto.GoogleGeocodeResponse
import java.math.BigDecimal

interface GoogleMapsApiPort {
    fun geocode(latitude: BigDecimal, longitude: BigDecimal): GoogleGeocodeResponse
}