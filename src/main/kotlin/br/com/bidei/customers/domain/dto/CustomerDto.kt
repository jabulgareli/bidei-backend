package br.com.bidei.customers.domain.dto

import br.com.bidei.address.domain.model.City
import br.com.bidei.customers.domain.model.Customer
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.NotNull

data class CustomerDto(
        @NotNull
        val name: String,

        @NotNull
        val email: String,

        @NotNull
        val phone: String,

        @NotNull
        @SerializedName("city_id")
        @JsonProperty("city_id")
        val cityId: Long,

        @NotNull
        @SerializedName("reference_id")
        @JsonProperty("reference_id")
        val referenceId: String,

        @NotNull
        val provider: String
) {
        fun toCustomer(city: City) =
                Customer(
                        id = UUID.randomUUID(),
                        name = this.name,
                        email = this.email,
                        phone =  this.phone,
                        city = city,
                        referenceId = this.referenceId,
                        provider = this.provider
                )

        fun isValidProvider(): Boolean {
                return try {
                        CustomerProviderEnum.valueOf(this.provider)
                        true
                } catch (e: Exception) {
                        false
                }
        }

}