package br.com.bidei.wallet.application.ports

import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface WalletStatementService {
    fun newRecordCardTransaction(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto): WalletStatement
    fun newWalletBalanceDebitTransaction(walletCustomer: WalletCustomer, walletBalanceDebitDto: WalletBidDebitDto)
    fun newWalletCouponCreditTransaction(walletCustomer: WalletCustomer, walletCouponCreditBidDto: WalletCouponCreditBidDto)
    fun newAuctionPaymentTransaction(walletCustomer: WalletCustomer, walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto, walletChargeResponseDto: WalletChargeResponseDto)
    fun listWalletTransactionsByCustomer(walletCustomerId: UUID, pageable: Pageable): Page<WalletStatement>
}