package br.com.bidei.integrations.payments.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class IuguPaymentMethodsRequest(
        val description: String,
        val token: String,
        @SerializedName("set_as_default")
        val setAsDefault: Boolean? = false
)