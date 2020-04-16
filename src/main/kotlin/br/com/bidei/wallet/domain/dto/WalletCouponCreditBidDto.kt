package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.constants.BidConfig
import java.math.BigDecimal
import java.util.*

data class WalletCouponCreditBidDto (
        val customerId: UUID,
        val coupon: String,
        val bids: BigDecimal = BigDecimal.ONE,
        val source: WalletChargeEnum = WalletChargeEnum.COUPON,
        val operationDescription: String = "Cupom aplicado"
){
    fun getAmount(): BigDecimal {
        return  bids.multiply(BidConfig.BID_UNIT_PRICE)
    }
}