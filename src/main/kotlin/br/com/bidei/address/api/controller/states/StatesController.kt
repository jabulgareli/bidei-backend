package br.com.bidei.address.api.controller.states

import br.com.bidei.address.domain.model.State
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/public/api/v1/address")
interface StatesController {

    @GetMapping("/states")
    fun states(): ResponseEntity<MutableList<State>>

}