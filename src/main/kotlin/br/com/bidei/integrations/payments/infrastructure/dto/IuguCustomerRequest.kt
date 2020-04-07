package br.com.bidei.integrations.payments.infrastructure.dto

import br.com.bidei.customers.domain.model.Customer
import com.google.gson.annotations.SerializedName

data class IuguCustomerRequest(
        val email: String,
        val name: String,
        val phone: String,
        @SerializedName("phone_prefix")
        val phonePrefix: String
){
        object Map {
                fun from(customer: Customer) =
                        IuguCustomerRequest(customer.email, customer.name, customer.phone(), customer.phonePrefix())
        }
}