package br.com.bidei.wallet.application.ports

import br.com.bidei.wallet.domain.dto.WalletBalanceDebitDto
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.model.WalletStatement

interface WalletStatementService {
    fun newRecordCardTransaction(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto)
    fun newWalletBalanceDebitTransaction(walletCustomer: WalletCustomer, walletBalanceDebitDto: WalletBalanceDebitDto)
}