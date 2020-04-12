package br.com.bidei.auction.domain.dto

import javax.validation.constraints.NotEmpty

data class AuctionPhotoDto(
        @get:NotEmpty(message = "Image is empty")
        val image: String? = ""
)