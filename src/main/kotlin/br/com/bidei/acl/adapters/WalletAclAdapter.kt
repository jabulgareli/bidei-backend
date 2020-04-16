package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.wallet.application.ports.WalletService
import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import org.springframework.stereotype.Service

@Service
class WalletAclAdapter(
        private val walletService: WalletService) : WalletAclPort {

    override fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto) {
        walletService.newBidDebitTransaction(walletBalanceDebitDto)
    }

    override fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto) {
        walletService.newCouponCreditTransaction(walletCouponCreditBidDto)
    }
}