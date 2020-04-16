package br.com.bidei.wallet.application.ports

import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface WalletStatementService {
    fun newRecordCardTransaction(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto): WalletStatement
    fun newWalletBalanceDebitTransaction(walletCustomer: WalletCustomer, walletBalanceDebitDto: WalletBidDebitDto)
    fun newWalletCouponCreditTransaction(walletCustomer: WalletCustomer, walletCouponCreditBidDto: WalletCouponCreditBidDto)
    fun listWalletTransactionsByCustomer(walletCustomerId: UUID, pageable: Pageable): Page<WalletStatement>
}