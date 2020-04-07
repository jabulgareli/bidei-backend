package br.com.bidei.integrations.payments.infrastructure.dto

import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.wallet.domain.dto.CreateCardDto
import com.google.gson.annotations.SerializedName

data class IuguPaymentTokenRequest(
        @SerializedName("account_id")
        val accountId: String,
        val method: String? = "credit_card",
        val test: Boolean? = true,
        val data: IuguPaymentTokenData
) {
        object Map {
                fun from(iuguConfig: IuguConfig, card: CreateCardDto) =
                        IuguPaymentTokenRequest(
                                accountId = iuguConfig.accountId.toString(),
                                test = iuguConfig.test,
                                data = IuguPaymentTokenData(card.number, card.verificationValue, card.firstName, card.lastName, card.month, card.year)
                        )
        }
}

data class IuguPaymentTokenData(
        val number: String,
        @SerializedName("verification_value")
        val verificationValue: String,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
        val month: String,
        val year: String
)