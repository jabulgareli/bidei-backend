package br.com.bidei.address.domain.ports.services

import br.com.bidei.address.domain.model.City

interface CitiesService {
    fun findByStateEqualsAndNameContains(state: String, name: String): MutableList<City>
}