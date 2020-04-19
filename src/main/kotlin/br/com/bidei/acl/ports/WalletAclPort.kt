package br.com.bidei.acl.ports

import br.com.bidei.wallet.domain.dto.WalletAuctionPaymentTransactionDto
import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import java.util.*

interface WalletAclPort {
    fun isWalletCreated(customerId: UUID): Boolean
    fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto)
    fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto)
    fun newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto)
}