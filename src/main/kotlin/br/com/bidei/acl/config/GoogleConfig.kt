package br.com.bidei.address.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "google")
data class GoogleConfig(
        var key: String?,
        var mapsUrl: String?
)