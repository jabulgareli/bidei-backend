package br.com.bidei.wallet.domain.dto

import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeResponse
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class WalletChargeResponseDto(
        val message: String,
        @JsonIgnore
        val errors: String?,
        val success: Boolean,
        val url: String,
        val pdf: String,
        val identification: String?,
        @SerializedName("invoice_id")
        val invoiceId: String,
        val LR: String
) {
    object Map {
        fun from(iuguChargeResponse: IuguChargeResponse) =
                WalletChargeResponseDto(
                        iuguChargeResponse.message,
                        null,
                        iuguChargeResponse.success,
                        iuguChargeResponse.url,
                        iuguChargeResponse.pdf,
                        iuguChargeResponse.identification,
                        iuguChargeResponse.invoiceId,
                        iuguChargeResponse.LR
                )
    }
}