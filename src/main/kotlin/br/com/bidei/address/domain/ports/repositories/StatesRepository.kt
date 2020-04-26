package br.com.bidei.address.domain.ports.repositories

import br.com.bidei.address.domain.model.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StatesRepository : JpaRepository<State, Long> {
    fun findByInitials(state: String): Optional<State>
}