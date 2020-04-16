package br.com.bidei.coupon.api.controller

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.dto.CreateOrUpdateAuctionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import java.util.*
import javax.validation.Valid

interface CouponController {
    @PostMapping("/api/v1/coupon/{code}")
    fun apply(@RequestHeader("customerId") customerId: UUID,
              @PathVariable code: String): ResponseEntity<Unit>
}