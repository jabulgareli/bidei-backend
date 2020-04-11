package br.com.bidei.integrations.payments.infrastructure.dto

import br.com.bidei.wallet.constants.BidConfig
import br.com.bidei.wallet.domain.dto.WalletCardChargeDto
import br.com.bidei.wallet.domain.model.WalletCustomer
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class IuguChargeRequest(
        @SerializedName("customer_payment_method_id")
        val customerPaymentMethodId: String,
        @SerializedName("customer_id")
        val customerId: String,
        val email: String,
        val items: ArrayList<IuguChargeItemDto>
) {
        object Map {
                fun from(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto) =
                        IuguChargeRequest(
                                walletCardChargeDto.paymentMethodId,
                                walletCustomer.referenceId,
                                walletCardChargeDto.email,
                                arrayListOf(IuguChargeItemDto.Map.from(walletCardChargeDto.quantity, BidConfig.BID_UNIT_PRICE_IN_CENTS))
                        )
        }
}

data class IuguChargeItemDto(
        val description: String? = "Compra de BIDs em bidei.com.br",
        val quantity: Int,
        @SerializedName("price_cents")
        val priceCents: Int
) {
        object Map {
                fun from(quantity: BigDecimal, priceCents: Int) =
                        IuguChargeItemDto(quantity = quantity.toInt(), priceCents = priceCents)
        }
}