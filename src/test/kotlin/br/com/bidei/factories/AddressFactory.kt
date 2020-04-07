package br.com.bidei.factories

import br.com.bidei.address.domain.model.City
import br.com.bidei.address.domain.model.State

object AddressFactory{
    val validCityId = 3543402L;

    fun newValidState() = State(35, "São Paulo", "SP", "North")
    fun newValidCity(state: State) = City(validCityId, "Ribeirão Preto", state)
}