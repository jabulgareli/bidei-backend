package br.com.bidei.customers.domain.ports.repositories

import br.com.bidei.customers.domain.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomersRepository : JpaRepository<Customer, UUID> {
    fun findByEmail(email: String): Optional<Customer>
    fun findByReferenceId(tokenUID: String): Optional<Customer>
}