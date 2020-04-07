package br.com.bidei.address.application.services.states

import br.com.bidei.address.domain.model.State

interface StatesService {
    fun findAll(): MutableList<State>
}