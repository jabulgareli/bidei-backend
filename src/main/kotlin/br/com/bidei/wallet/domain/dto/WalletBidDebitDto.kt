package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.constants.PriceConfig
import java.math.BigDecimal
import java.util.*

data class WalletBidDebitDto  (
        val customerId: UUID,
        val bids: BigDecimal = BigDecimal.ONE,
        val source: WalletChargeEnum = WalletChargeEnum.BALANCE,
        val operationDescription: String = "BID realizado"
){
    fun getAmount(): BigDecimal{
        return  bids.negate().multiply(PriceConfig.BID_UNIT_PRICE)
    }
}