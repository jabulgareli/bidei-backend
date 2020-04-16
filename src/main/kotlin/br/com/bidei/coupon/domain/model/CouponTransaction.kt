package br.com.bidei.coupon.domain.model

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.utils.DateUtils
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class CouponTransaction (
        @Id
        val id: UUID? = UUID.randomUUID(),
        @OneToOne
        val coupon: Coupon,
        @OneToOne
        val customer: Customer,
        val createdAt: Date = DateUtils.utcNow()
)