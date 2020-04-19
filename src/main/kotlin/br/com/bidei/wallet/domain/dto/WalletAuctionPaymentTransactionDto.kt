package br.com.bidei.wallet.domain.dto

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.constants.PriceConfig

data class WalletAuctionPaymentTransactionDto (
        val customer: Customer,
        val paymentReferenceId: String,
        val operationDescription: String = "Compra do leilão em bidei.com.br",
        val source: WalletChargeEnum = WalletChargeEnum.IUGU

){
    fun getAmount() = PriceConfig.AUCTION_PRICE
}