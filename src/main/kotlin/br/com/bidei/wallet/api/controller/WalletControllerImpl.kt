package br.com.bidei.wallet.api.controller

import br.com.bidei.wallet.application.ports.WalletService
import br.com.bidei.wallet.domain.dto.CreateCardDto
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class WalletControllerImpl(
        private val walletService: WalletService
) : WalletController {

    override fun listPaymentMethods(customerId: UUID)
            = ResponseEntity.ok(walletService.listPaymentMethods(customerId))

    override fun createPaymentMethods(customerId: UUID, createCardDto: CreateCardDto): ResponseEntity<Unit> {
        createCardDto.customerId = customerId
        return ResponseEntity.ok(walletService.addCard(createCardDto))
    }

    override fun deletePaymentMethods(customerId: UUID, paymentMethodsId: String)
            = ResponseEntity.ok(walletService.removeCard(customerId, paymentMethodsId))

    override fun newCardTransaction(customerId: UUID, walletCardChargeDto: WalletCardChargeDto): ResponseEntity<WalletChargeResponseDto> {
        walletCardChargeDto.customerId = customerId
        return ResponseEntity.ok(walletService.newCardTransaction(walletCardChargeDto))
    }

}