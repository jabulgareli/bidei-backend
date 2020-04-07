package br.com.bidei.address.domain.dto

import br.com.bidei.address.domain.model.City
import br.com.bidei.address.domain.model.State

data class GeocodeDto(
        val state: State?,
        val city: City?
)