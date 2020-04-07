package br.com.bidei.infrastructure.beans

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    fun gson(builder: GsonBuilder): Gson? {
        return builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()
    }

    @Bean
    fun okHttpClient(): OkHttpClient? {
        return OkHttpClient.Builder().build()
    }

}