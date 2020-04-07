package br.com.bidei.security.application.config

import br.com.bidei.security.application.token.TokenFilter
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.google.common.base.Charsets
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class RestSecurityConfig(
        private val objectMapper: ObjectMapper,
        private val tokenFilter: TokenFilter,
        private val firebaseConfig: FirebaseConfig,
        private val gson: Gson
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun restAuthenticationEntryPoint(): AuthenticationEntryPoint? {
        return AuthenticationEntryPoint { _, httpServletResponse, _ ->
            val errorObject: MutableMap<String, Any> = HashMap()
            val errorCode = 401
            errorObject["message"] = "Access Denied"
            errorObject["error"] = HttpStatus.UNAUTHORIZED
            errorObject["code"] = errorCode
            errorObject["timestamp"] = Timestamp(Date().time)
            httpServletResponse.contentType = "application/json;charset=UTF-8"
            httpServletResponse.status = errorCode
            httpServletResponse.writer.write(objectMapper.writeValueAsString(errorObject))
        }
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
        http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Primary
    @Bean
    @Throws(IOException::class)
    fun firebaseInit() {
        // In this case is necessary to create a new object do gson.toJson() parse
        val config = FirebaseConfig(
                firebaseConfig.type,
                firebaseConfig.project_id,
                firebaseConfig.private_key_id,
                firebaseConfig.private_key,
                firebaseConfig.client_email,
                firebaseConfig.client_id,
                firebaseConfig.auth_uri,
                firebaseConfig.token_uri,
                firebaseConfig.auth_provider_x509_cert_url,
                firebaseConfig.client_x509_cert_url
        )
        val refreshToken = ByteArrayInputStream(gson.toJson(config).toByteArray(Charsets.UTF_8))
        val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build()
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
    }

}