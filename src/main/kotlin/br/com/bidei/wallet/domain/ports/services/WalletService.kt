package br.com.bidei.wallet.domain.ports.services

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.model.WalletCustomer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface WalletService {
    fun create(customer: Customer): WalletCustomer
    fun get(customerId: UUID): WalletDto
    fun hasWallet(customerId: UUID): Boolean
    fun addCard(createCardDto: CreateCardDto)
    fun listPaymentMethods(customerId: UUID): ArrayList<PaymentMethodsDto>
    fun removeCard(customerId: UUID, paymentMethodId: String)
    fun newCardTransaction(walletCardChargeDto: WalletCardChargeDto): WalletChargeResponseDto
    fun newBidDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto)
    fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto)
    fun newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto)
    fun listWalletTransactionsByCustomer(customerId: UUID, pageable: Pageable): Page<WalletTransactionsPerDateDto>
}