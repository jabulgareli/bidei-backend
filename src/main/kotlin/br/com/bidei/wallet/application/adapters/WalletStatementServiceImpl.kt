package br.com.bidei.wallet.application.adapters

import br.com.bidei.wallet.application.ports.WalletStatementService
import br.com.bidei.wallet.domain.dto.WalletBalanceDebitDto
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.model.WalletStatement
import br.com.bidei.wallet.domain.ports.repository.WalletStatementRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class WalletStatementServiceImpl(
        private val walletStatementRepository: WalletStatementRepository
) : WalletStatementService {

    override fun newRecordCardTransaction(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto) {
        walletStatementRepository.save(
                WalletStatement(
                        walletCustomer = walletCustomer,
                        source = walletCardChargeDto.source.name,
                        success = walletChargeResponseDto.success,
                        message = walletChargeResponseDto.message,
                        url = walletChargeResponseDto.url,
                        pdf = walletChargeResponseDto.pdf,
                        invoiceId = walletChargeResponseDto.invoiceId,
                        transactionCode = walletChargeResponseDto.LR,
                        bids = walletCardChargeDto.getBidsQuantity(),
                        amount = walletCardChargeDto.getAmount(),
                        operationDescription = walletCardChargeDto.operationDescription
                ))
    }

    override fun newWalletBalanceDebitTransaction(walletCustomer: WalletCustomer, walletBalanceDebitDto: WalletBalanceDebitDto) {
        walletStatementRepository.save(
                WalletStatement(
                        walletCustomer = walletCustomer,
                        message = walletBalanceDebitDto.operationDescription,
                        bids = walletBalanceDebitDto.bids,
                        success = true,
                        invoiceId = "",
                        pdf = "",
                        transactionCode = "",
                        source = walletBalanceDebitDto.source.name,
                        url = "",
                        amount = walletBalanceDebitDto.getAmount(),
                        operationDescription = walletBalanceDebitDto.operationDescription
                )
        )
    }

    override fun listWalletTransactionsByCustomer(walletCustomerId: UUID, pageable: Pageable) =
            walletStatementRepository.findByWalletCustomerId(walletCustomerId, pageable)

}