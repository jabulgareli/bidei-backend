package br.com.bidei.wallet.domain.dto

import br.com.bidei.wallet.constants.PriceConfig
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.util.*

data class WalletCardChargeDto(
        @SerializedName("customer_id")
        @JsonProperty("customer_id")
        var customerId: UUID? = null,

        val source: WalletChargeEnum,

        @SerializedName("payment_method_id")
        @JsonProperty("payment_method_id")
        val paymentMethodId: String, // Card Id on partner wallet

        val email: String,

        val quantity: BigDecimal,

        val operationDescription: String = "Compra de BIDs em bidei.com.br"
) {
        fun getBidsQuantity(): BigDecimal {
                return quantity
        }

        fun getAmount(): BigDecimal{
                return quantity.multiply(PriceConfig.BID_UNIT_PRICE)
        }
}