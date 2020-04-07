package br.com.bidei.factories

import br.com.bidei.address.domain.model.State

object StateFactory {

    fun newState() = State(
            35, "São Paulo", "SP", "Southeast"
    )

}