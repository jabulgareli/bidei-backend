package br.com.bidei.address.application.adapters.geocode

import br.com.bidei.acl.ports.GoogleMapsApiPort
import br.com.bidei.address.domain.dto.GeocodeDto
import br.com.bidei.address.domain.model.City
import br.com.bidei.address.domain.model.State
import br.com.bidei.address.domain.ports.services.GeocodeService
import br.com.bidei.address.domain.ports.repositories.CitiesRepository
import br.com.bidei.address.domain.ports.repositories.StatesRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class GeocodeServiceImpl(
        private val googleMapsApiPort: GoogleMapsApiPort,
        private val statesRepository: StatesRepository,
        private val citiesRepository: CitiesRepository
) : GeocodeService {

    override fun findByLatitudeLongitude(latitude: BigDecimal, longitude: BigDecimal): GeocodeDto {
        val googleGeocodeResponse = googleMapsApiPort.geocode(latitude, longitude)
        var state: State? = null
        var city: City? = null

        if (googleGeocodeResponse.state() != null) {
            val stateBd = statesRepository.findByInitials(googleGeocodeResponse.state().toString())
            if (stateBd.isPresent) state = stateBd.get()
        }

        if (googleGeocodeResponse.city() != null && state != null) {
            val cityBd = citiesRepository.findByNameAndStateInitials(googleGeocodeResponse.city().toString(), state.initials)
            if (cityBd.isPresent) city = cityBd.get()
        }

        return GeocodeDto(state, city)
    }

}