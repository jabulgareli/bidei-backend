package br.com.bidei.coupon.application.adapters

import br.com.bidei.coupon.application.ports.CouponPrizePartnerServiceAdapterPort
import br.com.bidei.coupon.domain.model.CouponPartnerPrize
import br.com.bidei.coupon.repository.CouponPartnerPrizeRepository
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class CouponPrizePartnerServiceAdapter(private val couponPartnerPrizeRepository: CouponPartnerPrizeRepository) : CouponPrizePartnerServiceAdapterPort{
    override fun prizePartner(customer: Customer, bidsPurchasedAmount: BigDecimal, statement: WalletStatement) {
        if (!customer.canPrizePartner())
            return

        val prize = customer.inviteCoupon?.getPrizeAmount(bidsPurchasedAmount)

        if (prize == null || prize <= BigDecimal.ZERO)
            return

        couponPartnerPrizeRepository.save(CouponPartnerPrize(
                UUID.randomUUID(),
                customer,
                prize,
                statement
        ))
    }

}