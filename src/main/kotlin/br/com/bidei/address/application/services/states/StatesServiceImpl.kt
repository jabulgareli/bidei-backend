package br.com.bidei.address.application.services.states

import br.com.bidei.address.application.services.states.StatesService
import br.com.bidei.address.domain.repository.StatesRepository
import org.springframework.stereotype.Service

@Service
class StatesServiceImpl(private val statesRepository: StatesRepository) : StatesService {

    override fun findAll() = statesRepository.findAll()

}