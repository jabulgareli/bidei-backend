package br.com.bidei.integrations.payments.infrastructure.dto

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class IuguCustomerResponse(
        val id: String?,
        val email: String?,
        val name: String?,
        val notes: String?,
        @SerializedName("created_at")
        val createdAt: Timestamp?,
        @SerializedName("updated_at")
        val updatedAt: Timestamp?,
        @SerializedName("cc_emails")
        val ccEmails: String?,
        @SerializedName("cpf_cnpj")
        val cpfCnpj: String?,
        @SerializedName("zip_code")
        val zipCode: String?,
        val number: String?,
        val complement: String?,
        val phone: String?,
        @SerializedName("phone_prefix")
        val phonePrefix: String?,
        @SerializedName("default_payment_method_id")
        val defaultPaymentMethodId: String?,
        @SerializedName("proxy_payments_from_customer_id")
        val proxyPaymentsFromCustomerId: String?,
        val city: String?,
        val state: String?,
        val district: String?,
        val street: String?
)