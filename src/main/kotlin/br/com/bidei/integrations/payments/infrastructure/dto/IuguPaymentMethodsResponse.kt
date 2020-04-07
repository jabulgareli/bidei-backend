package br.com.bidei.integrations.payments.infrastructure.dto

import com.google.gson.annotations.SerializedName

data class IuguPaymentMethodsResponse(
        val id: String?,
        val description: String?,
        @SerializedName("item_type")
        val itemType: String?,
        @SerializedName("customer_id")
        val customerId: String?,
        val data: IuguPaymentMethodsData?
)

data class IuguPaymentMethodsData(
        val brand: String?,
        @SerializedName("holder_name")
        val holderName: String?,
        @SerializedName("display_number")
        val displayNumber: String?,
        val bin: String?,
        val year: Int?,
        val month: Int?,
        val holder: String?,
        @SerializedName("last_digits")
        val lastDigits: String?,
        @SerializedName("first_digits")
        val firstDigits: String?,
        @SerializedName("masked_number")
        val maskedNumber: String?
)

