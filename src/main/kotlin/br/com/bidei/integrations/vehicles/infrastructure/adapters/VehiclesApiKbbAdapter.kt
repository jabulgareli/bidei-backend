package br.com.bidei.integrations.vehicles.infrastructure.adapters

import br.com.bidei.integrations.vehicles.infrastructure.config.KbbConfig
import br.com.bidei.integrations.vehicles.infrastructure.ports.VehiclesApiKbbPort
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service

@Service
class VehiclesApiKbbAdapter(
        private val kbbConfig: KbbConfig,
        private val gson: Gson,
        private val okHttpClient: OkHttpClient) : VehiclesApiKbbPort {

    private fun withSecurityTokenAndLanguage() =
            "?securityToken=${kbbConfig.securityToken}&language=${kbbConfig.language}"

    private fun getDataFromKbb(url: String): String? {
        val newUrl = "${kbbConfig.url}${url}"
        val body = okHttpClient
                .newCall(Request.Builder().url(newUrl).get().build())
                .execute().body?.string()
        return gson.fromJson(body, JsonObject::class.java).getAsJsonArray("Response").toString()
    }

    override fun getAllVehicleBrand(): String? =
            getDataFromKbb("/Gps/GetAllVehicleBrand${withSecurityTokenAndLanguage()}")

    override fun getAllVehicleModel(brandId: String): String? =
            getDataFromKbb("/Gps/GetAllVehicleModel${withSecurityTokenAndLanguage()}&brandId=${brandId}")

    override fun getAllVehicleVersion(modelId: String, year: Int): String? =
            getDataFromKbb("/Gps/GetAllVehicleVersion${withSecurityTokenAndLanguage()}&modelId=${modelId}&year=${year}")

    override fun getAllVehicleColor(): String? =
            getDataFromKbb("/Gps/GetAllVehicleColor${withSecurityTokenAndLanguage()}")

    override fun getAllEquipments(vehicleId: Int): String? =
            getDataFromKbb("/Gps/GetAllEquipments${withSecurityTokenAndLanguage()}&vehicleId=${vehicleId}")

    override fun getAllVehicleGrade(): String? =
            getDataFromKbb("/Gps/GetAllVehicleGrade${withSecurityTokenAndLanguage()}")

    override fun getAllVehiclePriceType(vehicleId: Int, vehiclePriceTypeID: Int): String? =
            getDataFromKbb("/Gps/GetAllVehiclePrices${withSecurityTokenAndLanguage()}&vehicleId=${vehicleId}&vehiclePriceTypeID=${vehiclePriceTypeID}")

}