package br.com.bidei.wallet.domain.ports.repository

import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface WalletStatementRepository : JpaRepository<WalletStatement, UUID>