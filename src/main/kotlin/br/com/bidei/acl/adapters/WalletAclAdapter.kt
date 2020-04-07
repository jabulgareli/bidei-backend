package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.wallet.application.ports.WalletService
import br.com.bidei.wallet.domain.dto.WalletBalanceDebitDto
import org.springframework.stereotype.Service

@Service
class WalletAclAdapter(
        private val walletService: WalletService) : WalletAclPort {

    override fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBalanceDebitDto) {
        walletService.newBalanceDebitTransaction(walletBalanceDebitDto)
    }
}