package br.com.bidei.healthcheck.api.controller

import br.com.bidei.healthcheck.repository.HealthCheckRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController (private val healthCheckRepository: HealthCheckRepository){
    @GetMapping("public/api/v1/healthcheck")
    fun check(): ResponseEntity<Void> {
        return try{
            healthCheckRepository.check()
            ResponseEntity.ok().build()
        }catch (e: Exception){
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
        }
    }
}