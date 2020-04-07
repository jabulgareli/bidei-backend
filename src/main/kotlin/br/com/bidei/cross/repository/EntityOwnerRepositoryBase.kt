package br.com.bidei.cross.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface EntityOwnerRepositoryBase<T, U> : JpaRepository<T, U> {
    fun findByIdAndCustomerId(id: UUID, customerId: UUID): Optional<T>
}