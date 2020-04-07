package br.com.bidei.wallet.domain.dto

import br.com.bidei.integrations.payments.infrastructure.dto.IuguPaymentMethodsResponse
import com.google.gson.annotations.SerializedName

data class PaymentMethodsDto(
        val id: String?,
        val description: String?,
        val data: PaymentMethodsData
) {
        object Map {
                fun from(paymentMethods : IuguPaymentMethodsResponse) =
                        PaymentMethodsDto(
                                id = paymentMethods.id,
                                description = paymentMethods.description,
                                data = PaymentMethodsData(
                                        paymentMethods.data?.brand,
                                        paymentMethods.data?.displayNumber,
                                        paymentMethods.data?.year,
                                        paymentMethods.data?.month,
                                        paymentMethods.data?.holder,
                                        paymentMethods.data?.maskedNumber
                                )
                        )
        }
}

data class PaymentMethodsData(
        val brand: String?,
        @SerializedName("display_number")
        val displayNumber: String?,
        val year: Int?,
        val month: Int?,
        val holder: String?,
        @SerializedName("masked_number")
        val maskedNumber: String?
)

