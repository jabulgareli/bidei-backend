package br.com.bidei.address.infrastructure.dto

import br.com.bidei.acl.dto.GoogleGeocodeResponse
import com.google.gson.Gson
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.Test

internal class GoogleGeocodeResponseTest {

    @Test
    fun convert() {
        val json = "{\n" +
                "\t\"plus_code\": {\n" +
                "\t\t\"compound_code\": \"Q5JG+FW Ribeirao Preto - Conquista, Ribeirão Preto - State of São Paulo, Brazil\",\n" +
                "\t\t\"global_code\": \"58CJQ5JG+FW\"\n" +
                "\t},\n" +
                "\t\"results\": [{\n" +
                "\t\t\"address_components\": [{\n" +
                "\t\t\t\t\"long_name\": \"195\",\n" +
                "\t\t\t\t\"short_name\": \"195\",\n" +
                "\t\t\t\t\"types\": [\"street_number\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"Rua Arnaud Capuzzo\",\n" +
                "\t\t\t\t\"short_name\": \"Rua Arnaud Capuzzo\",\n" +
                "\t\t\t\t\"types\": [\"route\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"Nova Aliança\",\n" +
                "\t\t\t\t\"short_name\": \"Nova Aliança\",\n" +
                "\t\t\t\t\"types\": [\"political\", \"sublocality\", \"sublocality_level_1\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"Ribeirão Preto\",\n" +
                "\t\t\t\t\"short_name\": \"Ribeirão Preto\",\n" +
                "\t\t\t\t\"types\": [\"administrative_area_level_2\", \"political\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"São Paulo\",\n" +
                "\t\t\t\t\"short_name\": \"SP\",\n" +
                "\t\t\t\t\"types\": [\"administrative_area_level_1\", \"political\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"Brazil\",\n" +
                "\t\t\t\t\"short_name\": \"BR\",\n" +
                "\t\t\t\t\"types\": [\"country\", \"political\"]\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"long_name\": \"14026-594\",\n" +
                "\t\t\t\t\"short_name\": \"14026-594\",\n" +
                "\t\t\t\t\"types\": [\"postal_code\"]\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"formatted_address\": \"Rua Arnaud Capuzzo, 195 - Nova Aliança, Ribeirão Preto - SP, 14026-594, Brazil\",\n" +
                "\t\t\"geometry\": {\n" +
                "\t\t\t\"location\": {\n" +
                "\t\t\t\t\"lat\": -21.2187936,\n" +
                "\t\t\t\t\"lng\": -47.8227002\n" +
                "\t\t\t},\n" +
                "\t\t\t\"location_type\": \"ROOFTOP\",\n" +
                "\t\t\t\"viewport\": {\n" +
                "\t\t\t\t\"northeast\": {\n" +
                "\t\t\t\t\t\"lat\": -21.21744461970849,\n" +
                "\t\t\t\t\t\"lng\": -47.8213512197085\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t\"southwest\": {\n" +
                "\t\t\t\t\t\"lat\": -21.2201425802915,\n" +
                "\t\t\t\t\t\"lng\": -47.8240491802915\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t},\n" +
                "\t\t\"place_id\": \"ChIJcT5yHDa5uZQRhNQWDgDpR7s\",\n" +
                "\t\t\"plus_code\": {\n" +
                "\t\t\t\"compound_code\": \"Q5JG+FW Ribeirao Preto - Conquista, Ribeirão Preto - State of São Paulo, Brazil\",\n" +
                "\t\t\t\"global_code\": \"58CJQ5JG+FW\"\n" +
                "\t\t},\n" +
                "\t\t\"types\": [\"street_address\"]\n" +
                "\t}],\n" +
                "\t\"status\": \"OK\"\n" +
                "}"
        val teste = Gson().fromJson(json, GoogleGeocodeResponse::class.java)
        assertNotNull(teste.state())
        assertNotNull(teste.city())
    }

}