package br.com.bidei.integrations.payments.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "iugu")
data class IuguConfig(
        var url: String?,
        var accountId: String?,
        var apiToken: String?,
        var test: Boolean?
)