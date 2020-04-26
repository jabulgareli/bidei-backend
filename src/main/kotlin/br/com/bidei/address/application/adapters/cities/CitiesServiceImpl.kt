package br.com.bidei.address.application.adapters.cities

import br.com.bidei.address.domain.ports.services.CitiesService
import br.com.bidei.address.domain.ports.repositories.CitiesRepository
import org.springframework.stereotype.Service

@Service
class CitiesServiceImpl(private val citiesRepository: CitiesRepository) : CitiesService {

    override fun findByStateEqualsAndNameContains(state: String, name: String) =
            citiesRepository.findByStateInitialsEqualsAndNameContains(state, name)

}