package br.com.bidei.address.application.services.cities

import br.com.bidei.address.domain.repository.CitiesRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CitiesServiceImpl(private val citiesRepository: CitiesRepository) : CitiesService {

    override fun findByStateEqualsAndNameContains(state: String, name: String) =
            citiesRepository.findByStateInitialsEqualsAndNameContains(state, name)

}