package br.com.bidei.integrations.payments.infrastructure.adapters

import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.*
import br.com.bidei.integrations.payments.infrastructure.exceptions.IuguComunicationException
import br.com.bidei.integrations.payments.infrastructure.ports.IuguApiPort
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
class IuguApiAdapter(
        private val iuguConfig: IuguConfig,
        private val gson: Gson,
        private val okHttpClient: OkHttpClient
) : IuguApiPort {

    private inline fun <reified T> Gson.fromJson(json: String?) = fromJson<T>(json, object: TypeToken<T>() {}.type)

    private fun withApiToken() = "?api_token=${iuguConfig.apiToken}"

    override fun createCustomer(iuguCustomerRequest: IuguCustomerRequest): IuguCustomerResponse {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .url("${iuguConfig.url}/v1/customers${withApiToken()}")
                                .post(gson.toJson(iuguCustomerRequest).toRequestBody())
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return gson.fromJson(response.body?.string(), IuguCustomerResponse::class.java)
    }

    override fun createPaymentToken(iuguPaymentTokenRequest: IuguPaymentTokenRequest): IuguPaymentTokenResponse {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .url("${iuguConfig.url}/v1/payment_token${withApiToken()}")
                                .post(gson.toJson(iuguPaymentTokenRequest).toRequestBody())
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return gson.fromJson(response.body?.string(), IuguPaymentTokenResponse::class.java)
    }

    override fun createPaymentMethods(iuguCustomerId: String, iuguPaymentMethodsRequest: IuguPaymentMethodsRequest): Boolean {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                                .url("${iuguConfig.url}/v1/customers/${iuguCustomerId}/payment_methods${withApiToken()}")
                                .post(gson.toJson(iuguPaymentMethodsRequest).toRequestBody())
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return true
    }

    override fun listPaymentMethods(iuguCustomerId: String): List<IuguPaymentMethodsResponse> {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .url("${iuguConfig.url}/v1/customers/${iuguCustomerId}/payment_methods${withApiToken()}")
                                .get()
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return gson.fromJson(response.body?.string())
    }

    override fun removePaymentMethods(iuguCustomerId: String, paymentMethodsId: String): Boolean {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .url("${iuguConfig.url}/v1/customers/${iuguCustomerId}/payment_methods/${paymentMethodsId}${withApiToken()}")
                                .delete()
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return true
    }

    override fun charge(iuguChargeRequest: IuguChargeRequest): IuguChargeResponse {
        val response = okHttpClient
                .newCall(
                        Request.Builder()
                                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                                .url("${iuguConfig.url}/v1/charge${withApiToken()}")
                                .post(gson.toJson(iuguChargeRequest).toRequestBody())
                                .build())
                .execute()
        if (!response.isSuccessful) throw IuguComunicationException()
        return gson.fromJson(response.body?.string(), IuguChargeResponse::class.java)
    }

}