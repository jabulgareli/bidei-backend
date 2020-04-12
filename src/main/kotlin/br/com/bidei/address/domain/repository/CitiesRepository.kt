package br.com.bidei.address.domain.repository

import br.com.bidei.address.domain.model.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CitiesRepository : JpaRepository<City, Long> {
    fun findByStateInitialsEqualsAndNameContains(state:String, name: String): MutableList<City>
    fun findByNameAndStateInitials(name: String, state: String): Optional<City>
}