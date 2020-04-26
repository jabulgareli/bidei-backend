package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.ports.services.WalletService
import br.com.bidei.wallet.domain.dto.WalletAuctionPaymentTransactionDto
import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletAclAdapter(
        private val walletService: WalletService) : WalletAclPort {
    override fun create(customer: Customer): WalletCustomer =
            walletService.create(customer)

    override fun hasWallet(customerId: UUID) =
            walletService.hasWallet(customerId)

    override fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto) {
        walletService.newBidDebitTransaction(walletBalanceDebitDto)
    }

    override fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto) {
        walletService.newCouponCreditTransaction(walletCouponCreditBidDto)
    }

    override fun newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto) {
        walletService.newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto)
    }
}