package br.com.bidei.factories

import br.com.bidei.address.domain.model.City

object CityFactory {

    fun newCity() = City(
            3543402, "Rbeirão Preto", StateFactory.newState()
    )

}