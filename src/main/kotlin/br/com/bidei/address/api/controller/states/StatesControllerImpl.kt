package br.com.bidei.address.api.controller.states

import br.com.bidei.address.domain.ports.services.StatesService
import br.com.bidei.address.domain.model.State
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StatesControllerImpl(private val statesService: StatesService) : StatesController {

    override fun states(): ResponseEntity<MutableList<State>> = ResponseEntity.ok(statesService.findAll())

}