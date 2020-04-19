package br.com.bidei.acl.ports

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.dto.WalletAuctionPaymentTransactionDto
import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import java.util.*

interface WalletAclPort {
    fun create(customer: Customer): WalletCustomer
    fun hasWallet(customerId: UUID): Boolean
    fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto)
    fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto)
    fun newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto)
}