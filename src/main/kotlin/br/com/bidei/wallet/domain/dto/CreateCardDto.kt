package br.com.bidei.wallet.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.util.*

data class CreateCardDto(
        @SerializedName("customer_id")
        @JsonProperty("customer_id")
        var customerId: UUID? = null,

        val number: String,

        @SerializedName("verification_value")
        @JsonProperty("verification_value")
        val verificationValue: String,

        @SerializedName("first_name")
        @JsonProperty("first_name")
        val firstName: String,

        @SerializedName("last_name")
        @JsonProperty("last_name")
        val lastName: String,

        val month: String,

        val year: String,

        val description: String
)