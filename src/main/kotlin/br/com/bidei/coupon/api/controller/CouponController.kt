package br.com.bidei.coupon.api.controller

import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.dto.CreateOrUpdateAuctionDto
import br.com.bidei.coupon.domain.dto.CouponTransactionDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

interface CouponController {
    @PostMapping("/api/v1/coupon/{code}")
    fun apply(@RequestHeader("customerId") customerId: UUID,
              @PathVariable code: String): ResponseEntity<Unit>

    @GetMapping("/api/v1/coupon/customer/{customerId}")
    fun getUsedCouponsByCustomerId(@RequestHeader("customerId") customerId: UUID): ResponseEntity<List<CouponTransactionDto>>
}