package br.com.bidei.integrations.payments.infrastructure.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class IuguChargeResponse(
        val message: String,
//        val errors: String?,
        val success: Boolean,
        val url: String,
        val pdf: String,
        val identification: String?,
        @SerializedName("invoice_id")
        @JsonProperty("invoice_id")
        val invoiceId: String,
        val LR: String
)