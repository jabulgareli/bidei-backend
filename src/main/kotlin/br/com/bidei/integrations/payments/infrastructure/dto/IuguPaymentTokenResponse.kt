package br.com.bidei.integrations.payments.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class IuguPaymentTokenResponse(
        val id: String?,
        val method: String? = "credit_card",
        val test: Boolean? = true,
        @SerializedName("extra_info")
        val extraInfo: IuguPaymentTokenExtraInfo?
)

data class IuguPaymentTokenExtraInfo(
        val bin: String?,
        val year: Int?,
        val month: Int?,
        val brand: String?,
        @SerializedName("holder_name")
        val holderName: String?,
        @SerializedName("display_number")
        val displayNumber: String?
)