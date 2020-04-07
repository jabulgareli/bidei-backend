package br.com.bidei.security.application.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "firebase")
data class FirebaseConfig(
        var type: String?,
        var project_id: String?,
        var private_key_id: String?,
        var private_key: String?,
        var client_email: String?,
        var client_id: String?,
        var auth_uri: String?,
        var token_uri: String?,
        var auth_provider_x509_cert_url: String?,
        var client_x509_cert_url: String?
)