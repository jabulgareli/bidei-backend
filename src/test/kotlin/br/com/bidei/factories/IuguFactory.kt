package br.com.bidei.factories

import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.*
import java.sql.Timestamp
import java.util.*

object IuguFactory {

    private val accountId = UUID.randomUUID().toString()
    private val token = UUID.randomUUID().toString()
    val customerId: UUID = CustomerFactory.customerId

    fun newIuguPaymentTokenRequest(iuguConfig: IuguConfig?) = IuguPaymentTokenRequest.Map.from(iuguConfig!!, WalletFactory.newCreateCardDto())

    fun newIuguPaymentTokenResponse() = IuguPaymentTokenResponse(id= "ID", extraInfo = null)

    fun newIuguPaymentMethodsRequest() = IuguPaymentMethodsRequest("description", token)

    fun newIuguPaymentMethodsResponse() = IuguPaymentMethodsResponse(
            "123ABC456DEF",
            "description",
            "credit_card",
            customerId.toString(),
            IuguPaymentMethodsData(
                    "brand", "holderName", "displayNumber", "bin", 2020, 3, "holder", "123", "123", "XXXX"
            )
    )

    fun newIuguConfig() = IuguConfig("url", accountId, token, true)

    fun newIuguCustomerResponse() =
            IuguCustomerResponse(
                    this.customerId.toString(),
                    "email",
                    "name",
                    "notes",
                    Timestamp(System.currentTimeMillis()),
                    Timestamp(System.currentTimeMillis()),
                    "ccEmails",
                    "cpfCnpj",
                    "zipCode",
                    "number",
                    "complement",
                    "phone",
                    "phonePrefix",
                    "defaultPaymentMethodId",
                    "proxyPaymentsFromCustomerId",
                    "city",
                    "state",
                    "district",
                    "street"
            )

    fun newIuguChargeResponse(success: Boolean) = IuguChargeResponse(
            "message",
            success,
            "url",
            "pdf",
            "identification",
            "invoiceid",
            "00"
    )

}