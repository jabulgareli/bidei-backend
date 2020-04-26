package br.com.bidei.wallet.application.adapters

import br.com.bidei.acl.ports.CouponAclPort
import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.cross.services.EntityOwnerServiceBase
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.*
import br.com.bidei.utils.DateUtils
import br.com.bidei.wallet.domain.ports.services.WalletService
import br.com.bidei.wallet.domain.ports.services.WalletStatementService
import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.exceptions.WalletNotFoundException
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.ports.repositories.WalletCustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class WalletServiceImpl(
        private val iuguConfig: IuguConfig,
        private val walletCustomerRepository: WalletCustomerRepository,
        private val integrationsPaymentsAcl: IntegrationsPaymentsAclPort,
        private val walletStatementService: WalletStatementService,
        private val couponAclPort: CouponAclPort
) : WalletService, EntityOwnerServiceBase<WalletCustomer, UUID>() {
    @Transactional
    override fun create(customer: Customer): WalletCustomer {
        val iuguCustomer: IuguCustomerResponse = integrationsPaymentsAcl.createCustomer(IuguCustomerRequest.Map.from(customer))

        return walletCustomerRepository.save(
                WalletCustomer(
                        customer = customer,
                        referenceId = iuguCustomer.id.toString()))
    }

    @Transactional
    override fun get(customerId: UUID) =
            WalletDto(findById(customerId).bids)

    @Transactional
    override fun hasWallet(customerId: UUID) =
            walletCustomerRepository.findByCustomerId(customerId).isPresent

    @Transactional
    override fun addCard(createCardDto: CreateCardDto) {
        val wallet = findById(createCardDto.customerId!!)
        val token = integrationsPaymentsAcl.createPaymentToken(IuguPaymentTokenRequest.Map.from(iuguConfig, createCardDto))
        integrationsPaymentsAcl.createPaymentMethods(wallet.referenceId, IuguPaymentMethodsRequest(createCardDto.description, token.id.toString()))
    }

    @Transactional
    override fun listPaymentMethods(customerId: UUID): ArrayList<PaymentMethodsDto> {
        val wallet = findById(customerId)
        val listPaymentMethods = integrationsPaymentsAcl.listPaymentMethods(wallet.referenceId)
        return listPaymentMethods.mapTo(arrayListOf()) {
            PaymentMethodsDto.Map.from(it)
        }
    }

    @Transactional
    override fun removeCard(customerId: UUID, paymentMethodId: String) {
        val wallet = findById(customerId)
        checkOwner(wallet.id!!, customerId)
        integrationsPaymentsAcl.removePaymentMethods(wallet.referenceId, paymentMethodId)
    }

    @Transactional
    override fun newCardTransaction(walletCardChargeDto: WalletCardChargeDto): WalletChargeResponseDto {
        val walletCustomer = findById(walletCardChargeDto.customerId!!)
        val iuguChargeResponse = integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))
        val walletChargeResponseDto = WalletChargeResponseDto.Map.from(iuguChargeResponse)
        chargeWalletWhenCard(walletCustomer, walletCardChargeDto, walletChargeResponseDto)
        return walletChargeResponseDto
    }

    @Transactional
    override fun newBidDebitTransaction(walletBalanceDebitDto: WalletBidDebitDto) {
        val walletCustomer = findById(walletBalanceDebitDto.customerId)
        walletStatementService.newWalletBalanceDebitTransaction(walletCustomer, walletBalanceDebitDto)
        walletCustomer.chargeWallet(walletBalanceDebitDto.bids.negate())
        walletCustomerRepository.save(walletCustomer)
    }

    @Transactional
    override fun newCouponCreditTransaction(walletCouponCreditBidDto: WalletCouponCreditBidDto) {
        val walletCustomer = findById(walletCouponCreditBidDto.customerId)
        walletStatementService.newWalletCouponCreditTransaction(walletCustomer, walletCouponCreditBidDto)
        walletCustomer.chargeWallet(walletCouponCreditBidDto.bids)
        walletCustomerRepository.save(walletCustomer)
    }

    @Transactional
    override fun newAuctionPaymentTransaction(walletAuctionPaymentTransactionDto: WalletAuctionPaymentTransactionDto) {
        val walletCustomer = findById(walletAuctionPaymentTransactionDto.customer.id)

        val iuguChargeResponse = integrationsPaymentsAcl.charge(IuguChargeRequest.Map.forAuctionPayment(walletCustomer,
                walletAuctionPaymentTransactionDto.paymentReferenceId,
                walletAuctionPaymentTransactionDto.customer))

        val walletChargeResponseDto = WalletChargeResponseDto.Map.from(iuguChargeResponse)

        walletStatementService.newAuctionPaymentTransaction(walletCustomer,
                walletAuctionPaymentTransactionDto,
                walletChargeResponseDto)
    }

    @Transactional
    override fun listWalletTransactionsByCustomer(customerId: UUID, pageable: Pageable): Page<WalletTransactionsPerDateDto> {
        val wallet = findById(customerId)
        val statements = walletStatementService.listWalletTransactionsByCustomer(wallet.id!!, pageable)

        val aggregate = statements.groupBy { t -> DateUtils.dateWithoutTime(t.createdAt!!) }
                .map { t ->
                    WalletTransactionsPerDateDto(t.key,
                            t.value.map { w -> WalletTransactionDto.Map.fromWalletStatement(w) })
                }
        return PageImpl(aggregate, pageable, aggregate.size.toLong())
    }


    private fun chargeWalletWhenCard(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto) {
        val statement = walletStatementService.newRecordCardTransaction(walletCustomer, walletCardChargeDto, walletChargeResponseDto)

        if (walletChargeResponseDto.success) {
            walletCustomer.chargeWallet(walletCardChargeDto.getBidsQuantity())

            walletCustomerRepository.save(walletCustomer)

            couponAclPort.prizePartner(walletCustomer.customer,
                    walletCardChargeDto.getAmount(),
                    statement)
        }
    }

    private fun findById(customerId: UUID) =
            walletCustomerRepository.findByCustomerId(customerId).orElseThrow { WalletNotFoundException() }


}