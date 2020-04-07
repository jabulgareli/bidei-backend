package br.com.bidei.integrations.vehicles.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "kbb")
data class KbbConfig(
        var url: String?,
        var securityToken: String?,
        var language: String?
)