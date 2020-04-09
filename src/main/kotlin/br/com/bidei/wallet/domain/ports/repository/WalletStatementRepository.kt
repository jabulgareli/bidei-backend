package br.com.bidei.wallet.domain.ports.repository

import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WalletStatementRepository : JpaRepository<WalletStatement, UUID>, PagingAndSortingRepository<WalletStatement, UUID>{
    fun findByWalletCustomerId(walletCustomerId: UUID, pageable: Pageable): Page<WalletStatement>
}