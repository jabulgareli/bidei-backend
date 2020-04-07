package br.com.bidei.address.application.services.cities

import br.com.bidei.address.domain.model.City
import java.util.*

interface CitiesService {
    fun findByStateEqualsAndNameContains(state: String, name: String): MutableList<City>
}