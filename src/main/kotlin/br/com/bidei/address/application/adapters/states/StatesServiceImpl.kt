package br.com.bidei.address.application.adapters.states

import br.com.bidei.address.domain.ports.services.StatesService
import br.com.bidei.address.domain.ports.repositories.StatesRepository
import org.springframework.stereotype.Service

@Service
class StatesServiceImpl(private val statesRepository: StatesRepository) : StatesService {

    override fun findAll() = statesRepository.findAll()

}