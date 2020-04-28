package br.com.bidei.healthcheck.repository

import br.com.bidei.auction.domain.model.Auction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface HealthCheckRepository : JpaRepository<Auction, UUID> {
    @Query("SELECT 1", nativeQuery = true)
    fun check()
}