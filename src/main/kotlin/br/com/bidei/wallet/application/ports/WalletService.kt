package br.com.bidei.wallet.application.ports

import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface WalletService {
    fun addCard(createCardDto: CreateCardDto)
    fun listPaymentMethods(customerId: UUID): ArrayList<PaymentMethodsDto>
    fun removeCard(customerId: UUID, paymentMethodId: String)
    fun newCardTransaction(walletCardChargeDto: WalletCardChargeDto): WalletChargeResponseDto
    fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBalanceDebitDto)
    fun listWalletTransactionsByCustomer(customerId: UUID, pageable: Pageable): Page<WalletTransactionsPerDateDto>
}