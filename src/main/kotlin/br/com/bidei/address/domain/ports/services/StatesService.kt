package br.com.bidei.address.domain.ports.services

import br.com.bidei.address.domain.model.State

interface StatesService {
    fun findAll(): MutableList<State>
}