package br.com.bidei.auction.application.dto

import java.util.*
import javax.validation.constraints.NotEmpty

data class AuctionPhotoDto(
        @get:NotEmpty(message = "Image is empty")
        val image: String? = ""
)