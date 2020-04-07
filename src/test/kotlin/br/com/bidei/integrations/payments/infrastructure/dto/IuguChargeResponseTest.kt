package br.com.bidei.integrations.payments.infrastructure.dto

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class IuguChargeResponseTest {

    @Test
    fun `Parse object test`() {
        val json = "{\"message\":\"Autorizado\",\"errors\":{},\"success\":true,\"url\":\"https://faturas.iugu.com/94636b1c-2c39-436e-a0d0-150f403828ab-480b\",\"pdf\":\"https://faturas.iugu.com/94636b1c-2c39-436e-a0d0-150f403828ab-480b.pdf\",\"identification\":null,\"invoice_id\":\"94636B1C2C39436EA0D0150F403828AB\",\"LR\":\"00\"}"
        val iuguChargeResponse = Gson().fromJson(json, IuguChargeResponse::class.java)
        assertNotNull(iuguChargeResponse)
    }

}