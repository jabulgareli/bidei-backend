package br.com.bidei.acl.dto

import com.google.gson.annotations.SerializedName

data class GoogleGeocodeResponse(
        val results: ArrayList<GoogleGeocodeAddressComponents>
) {
        fun state() = findByType("administrative_area_level_1")

        fun city() = findByType("administrative_area_level_2")

        private fun findByType(type: String): String? {
                var value: String? =  null
                results[0]
                        .addressComponents
                        .filter { it.types.contains(type) }
                        .forEach {  value = it.shortName }
                return value
        }
}

data class GoogleGeocodeAddressComponents(
        @SerializedName("address_components")
        val addressComponents: ArrayList<GoogleGeocodeComponent>
)

data class GoogleGeocodeComponent(
        @SerializedName("long_name")
        val longName: String,
        @SerializedName("short_name")
        val shortName: String,
        val types: List<String>
)