package br.com.bidei.acl.adapters

import br.com.bidei.acl.dto.GoogleGeocodeResponse
import br.com.bidei.acl.ports.GoogleMapsApiPort
import br.com.bidei.address.infrastructure.config.GoogleConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class GoogleMapsApiAdapter(
        private val googleConfig: GoogleConfig,
        private val gson: Gson,
        private val okHttpClient: OkHttpClient
) : GoogleMapsApiPort {

    private fun withKey() = "&key=${googleConfig.key}"

    override fun geocode(latitude: BigDecimal, longitude: BigDecimal): GoogleGeocodeResponse {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .url("${googleConfig.mapsUrl}/api/geocode/json?&latlng=$latitude,$longitude${withKey()}")
                                .get()
                                .build())
                .execute()
        return gson.fromJson(response.body?.string(), GoogleGeocodeResponse::class.java)
    }

}