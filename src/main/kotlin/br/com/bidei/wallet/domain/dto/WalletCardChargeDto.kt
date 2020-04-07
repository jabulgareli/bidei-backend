package br.com.bidei.wallet.domain.dto

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

        val quantity: BigDecimal
) {
        fun getBidsQuantity(): BigDecimal {
                return quantity
        }
}