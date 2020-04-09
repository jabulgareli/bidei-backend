package br.com.bidei.wallet.api.controller

import br.com.bidei.wallet.domain.dto.CreateCardDto
import br.com.bidei.wallet.domain.dto.PaymentMethodsDto
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("api/v1/wallet")
interface WalletController {

    @GetMapping("/{customerId}/payment-methods")
    fun listPaymentMethods(@PathVariable customerId: UUID): ResponseEntity<ArrayList<PaymentMethodsDto>>

    @GetMapping("/{customerId}/transactions")
    fun listWalletTransactionsByCustomer(@PathVariable customerId: UUID,
                                         @RequestParam(value = "page", defaultValue = "0") page: Int,
                                         @RequestParam(value = "orderBy", defaultValue = "id") sortBy: String,
                                         @RequestParam(value = "direction", defaultValue = "DESC") direction: String,
                                         @RequestParam(value = "perPage", defaultValue = "5") perPage: Int): ResponseEntity<Page<WalletStatement>>

    @PostMapping("/payment-methods")
    fun createPaymentMethods(@RequestHeader("customerId") customerId: UUID, @RequestBody createCardDto: CreateCardDto): ResponseEntity<Unit>

    @DeleteMapping("/{customerId}/payment-methods/{paymentMethodsId}")
    fun deletePaymentMethods(@PathVariable customerId: UUID, @PathVariable paymentMethodsId: String): ResponseEntity<Unit>

    @PostMapping("/card/transaction")
    fun newCardTransaction(@RequestHeader("customerId") customerId: UUID, @RequestBody walletCardChargeDto: WalletCardChargeDto): ResponseEntity<WalletChargeResponseDto>

}