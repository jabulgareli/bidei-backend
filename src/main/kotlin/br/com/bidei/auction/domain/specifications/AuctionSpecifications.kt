package br.com.bidei.auction.domain.specifications

import br.com.bidei.address.domain.model.City
import br.com.bidei.address.domain.model.State
import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import org.springframework.data.jpa.domain.Specification
import java.sql.Timestamp
import java.util.*

object AuctionSpecifications{
    fun emptySpecification(): Specification<Auction> = Specification<Auction> { _, _, _ -> null }

    fun withCityId(cityId: Long): Specification<Auction> = Specification{
        root, _, criteriaBuilder ->
            if (cityId > 0) criteriaBuilder.equal(root.get<City>("city").get<Long>("id"), cityId) else null
    }

    fun withCarBrand(carBrand: String): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
            if (carBrand.isNotEmpty()) criteriaBuilder.like(criteriaBuilder.upper(root.get("carBrand")), carBrand.toUpperCase()) else null
    }

    fun withCarModel(carModel: String): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
            if (carModel.isNotEmpty()) criteriaBuilder.like(criteriaBuilder.upper(root.get("carModel")), carModel.toUpperCase()) else null
    }

    fun withMinFabricationYear(minFabricationYear: Int): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
            if (minFabricationYear > 1950) criteriaBuilder.greaterThanOrEqualTo(root.get("carFabricationYear"), minFabricationYear) else null
    }

    fun withMaxFabricationYear(maxFabricationYear: Int): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        if (maxFabricationYear < DateUtils.getCurrentYear() && maxFabricationYear > 0) criteriaBuilder.lessThanOrEqualTo(root.get("carFabricationYear"), maxFabricationYear) else null
    }

    fun withMinKm(minKm: Int): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        if (minKm > 0) criteriaBuilder.greaterThanOrEqualTo(root.get("carKm"), minKm) else null
    }

    fun withMaxKm(maxKm: Int): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
            if (maxKm in 1..219999) criteriaBuilder.lessThanOrEqualTo(root.get("carKm"), maxKm) else null
    }

    fun withStateId(stateId: Long): Specification<Auction> = Specification{
        root, _, criteriaBuilder ->
        if (stateId > 0) criteriaBuilder.equal(root.get<City>("city").get<State>("state").get<Long>("id"), stateId) else null
    }

    fun withCustomerId(id: UUID): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Customer>("customer").get<UUID>("id"), id)
    }

    fun isOpen(): Specification<Auction> = Specification.where(isNotExpired())!!.and(isNotManuallyFinished())!!

    fun isExpired(): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), DateUtils.utcNow())
    }

    fun isNotExpired(): Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), DateUtils.utcNow())
    }

    fun isManuallyFinished():  Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.isNotNull(root.get<Date?>("manuallyFinishedAt"))
    }

    fun isNotManuallyFinished():  Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.isNull(root.get<Date?>("manuallyFinishedAt"))
    }

    fun isRegisterFinished():  Specification<Auction> = Specification {
        root, _, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Boolean>("isRegisterFinished"), true)
    }

}