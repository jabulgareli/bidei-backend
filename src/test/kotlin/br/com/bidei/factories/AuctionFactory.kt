package br.com.bidei.factories

import br.com.bidei.address.domain.model.City
import br.com.bidei.auction.domain.dto.AuctionPhotoDto
import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.customers.domain.model.Customer
import java.math.BigDecimal
import java.util.*

object AuctionFactory{
    val validAuctionId = UUID.fromString("7dab9bb8-5b89-42b4-9436-85691c5d9251")
    val defaultCarStartPrice = BigDecimal.valueOf(58500.99)
    fun getDaysFromNow(daysToAdd: Int?): Date?{
        if (daysToAdd == null)
            return null
        val date = Calendar.getInstance()
        date.add(Calendar.DAY_OF_YEAR, daysToAdd)
        return date.time
    }

    fun newAuction(daysToEndDate: Int, customer: Customer, city: City, finishedDaysAgo: Int? = null) =
            Auction(validAuctionId,
                    customer,
                    city,
                    getDaysFromNow(daysToEndDate)!!,
                    null,
                    defaultCarStartPrice,
                    "Chevrolet",
                    "Cruze",
                    "LT 1.8",
                    2016,
                    2016,
                    "Flex",
                    61500,
                    null,
                    "Automática",
                    "[]",
                    getDaysFromNow(finishedDaysAgo),
                    null,
                    defaultCarStartPrice)

    fun newAuctionPhotoDto() = AuctionPhotoDto("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")
}