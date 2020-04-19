package br.com.bidei.auction.domain.model

import br.com.bidei.address.domain.model.City
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
data class Auction (
        @Id
        val id: UUID? = UUID.randomUUID(),
        @OneToOne
        val customer: Customer,
        @OneToOne
        val city: City,
        val endDate: Timestamp,
        val startPrice: BigDecimal,
        val carBrand: String,
        val carModel: String,
        val carVersion: String,
        val carFabricationYear: Int,
        val carModelYear: Int,
        val carFuelType: String,
        val carKm: Int = 0,
        @Lob
        val carOptions: String? = "[]",
        val carTransmission: String,
        @Lob
        val carConditions: String? = "[]",
        var manuallyFinishedAt: Timestamp?,
        val createdDate: Timestamp? = DateUtils.utcNow(),
        var currentPrice: BigDecimal? = startPrice,
        val productType: AuctionProductType? = AuctionProductType.CAR,
        @Lob
        val carCharacteristics: String? = "[]",
        val carIsArmored: Boolean? = false,
        var isPaid: Boolean? = false,
        val carColor: String? = null,
        val isRegisterFinished: Boolean? = false,
        @Lob
        var photos: String? = "[]") {


    fun finish() {
        manuallyFinishedAt = DateUtils.utcNow()
    }

    fun isFinished(): Boolean = ((endDate <= DateUtils.utcNow()) ||
                                 (manuallyFinishedAt != null))

    fun acceptBid(value: BigDecimal) {
        if (currentPrice == null)
            currentPrice = startPrice

        currentPrice = currentPrice?.plus(value)
    }
}