package br.com.bidei.address.application.adapters.states

import br.com.bidei.address.application.ports.states.StatesController
import br.com.bidei.address.application.services.states.StatesService
import br.com.bidei.address.domain.model.State
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StatesControllerImpl(private val statesService: StatesService) : StatesController {

    override fun states(): ResponseEntity<MutableList<State>> = ResponseEntity.ok(statesService.findAll())

}