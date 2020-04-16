package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.constants.BidConfig
import java.math.BigDecimal
import java.util.*

data class WalletBidDebitDto  (
        val customerId: UUID,
        val bids: BigDecimal = BigDecimal.ONE,
        val source: WalletChargeEnum = WalletChargeEnum.BALANCE,
        val operationDescription: String = "BID realizado"
){
    fun getAmount(): BigDecimal{
        return  bids.negate().multiply(BidConfig.BID_UNIT_PRICE)
    }
}