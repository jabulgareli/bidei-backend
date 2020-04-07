package br.com.bidei.acl.ports

import br.com.bidei.wallet.domain.dto.WalletBalanceDebitDto

interface WalletAclPort {
    fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBalanceDebitDto)
}