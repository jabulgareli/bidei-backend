package br.com.bidei.coupon.application.adapters

import br.com.bidei.acl.ports.BidAclPort
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.coupon.application.ports.CouponServicePort
import br.com.bidei.coupon.domain.dto.CouponTransactionDto
import br.com.bidei.coupon.domain.exception.CouponNotFoundException
import br.com.bidei.coupon.domain.exception.CustomerAlreadyBidedException
import br.com.bidei.coupon.domain.exception.CustomerAlreadyInvitedException
import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.coupon.domain.model.CouponTransaction
import br.com.bidei.coupon.repository.CouponRepository
import br.com.bidei.coupon.repository.CouponTransactionRepository
import br.com.bidei.customers.application.exceptions.CustomerNotFoundException
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.dto.WalletCouponCreditBidDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@Service
class CouponServiceAdapter(private val couponRepository: CouponRepository,
                           private val couponTransactionRepository: CouponTransactionRepository,
                           private val walletAclPort: WalletAclPort,
                           private val customersAclPort: CustomersAclPort,
                           private val bidAclPort: BidAclPort) : CouponServicePort {

    @Transactional
    override fun apply(customerId: UUID, code: String) {
        val customer = customersAclPort.findById(customerId).orElseThrow { CustomerNotFoundException() }
        val coupon = couponRepository.findByCode(code).orElseThrow { CouponNotFoundException() }
        val usageCount = couponTransactionRepository.countByCustomerIdAndCoupon_Code(customerId, code)

        coupon.canBeUsed(usageCount)

        if (coupon.isInvite())
            applyInvite(customer, coupon)

        sendBidsToCustomer(customer, coupon)

        coupon.incUsed()
        couponRepository.save(coupon)
    }

    override fun getUsedCouponsByCustomerId(customerId: UUID): List<CouponTransactionDto> {
        return couponTransactionRepository.findByCustomerId(customerId).map { t -> CouponTransactionDto.Map.fromCouponTransaction(t) }
    }

    private fun applyInvite(customer: Customer, coupon: Coupon) {
        if (customer.isInvited())
            throw CustomerAlreadyInvitedException()

        val qtdCustomerBids = bidAclPort.countByCustomerId(customer.id)

        if (qtdCustomerBids > 0)
            throw CustomerAlreadyBidedException()

        customersAclPort.receiveInviteFromPartner(customer, coupon)
    }

    private fun sendBidsToCustomer(customer: Customer, coupon: Coupon) {

        couponTransactionRepository.save(CouponTransaction(coupon = coupon,
                customer = customer))

        walletAclPort.newCouponCreditTransaction(WalletCouponCreditBidDto(
                customer.id,
                coupon.code,
                BigDecimal(coupon.bidPrize)
        ))
    }

}