package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.CouponAclPort
import br.com.bidei.coupon.application.ports.CouponPrizePartnerServiceAdapterPort
import br.com.bidei.coupon.application.ports.CouponServicePort
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.model.WalletStatement
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CouponAclAdapter(private val couponPrizePartnerServiceAdapterPort: CouponPrizePartnerServiceAdapterPort) : CouponAclPort {
    override  fun prizePartner(customer: Customer, bidsPurchasedAmount: BigDecimal, statement: WalletStatement){
        couponPrizePartnerServiceAdapterPort.prizePartner(customer, bidsPurchasedAmount, statement)
    }
}