package br.com.bidei.customers.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.NotNull

data class CustomerUpdateDto(

        var customerId: UUID? = null,

        @NotNull
        val name: String,

        @NotNull
        @SerializedName("city_id")
        @JsonProperty("city_id")
        val cityId: Long
)