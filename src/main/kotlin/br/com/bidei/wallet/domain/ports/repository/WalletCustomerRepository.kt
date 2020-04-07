package br.com.bidei.wallet.domain.ports.repository

import br.com.bidei.cross.repository.EntityOwnerRepositoryBase
import br.com.bidei.wallet.domain.model.WalletCustomer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WalletCustomerRepository : JpaRepository<WalletCustomer, UUID>, EntityOwnerRepositoryBase<WalletCustomer, UUID> {
    fun findByCustomerId(customerId: UUID): Optional<WalletCustomer>
}