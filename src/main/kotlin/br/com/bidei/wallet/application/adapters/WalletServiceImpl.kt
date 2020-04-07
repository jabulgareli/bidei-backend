package br.com.bidei.wallet.application.adapters

import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.cross.services.EntityOwnerServiceBase
import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.*
import br.com.bidei.wallet.application.ports.WalletService
import br.com.bidei.wallet.application.ports.WalletStatementService
import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.ports.repository.WalletCustomerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class WalletServiceImpl(
        private val iuguConfig: IuguConfig,
        private val walletCustomerRepository: WalletCustomerRepository,
        private val integrationsPaymentsAcl: IntegrationsPaymentsAclPort,
        private val customersAcl: CustomersAclPort,
        private val walletStatementService: WalletStatementService
) : WalletService, EntityOwnerServiceBase<WalletCustomer, UUID>() {

    override fun addCard(createCardDto: CreateCardDto) {
        val wallet = verifyOrCreateWalletAccount(createCardDto.customerId!!)
        val token = integrationsPaymentsAcl.createPaymentToken(IuguPaymentTokenRequest.Map.from(iuguConfig, createCardDto))
        integrationsPaymentsAcl.createPaymentMethods(wallet.referenceId, IuguPaymentMethodsRequest(createCardDto.description, token.id.toString()))
    }

    override fun listPaymentMethods(customerId: UUID): ArrayList<PaymentMethodsDto> {
        val wallet = verifyOrCreateWalletAccount(customerId)
        val listPaymentMethods = integrationsPaymentsAcl.listPaymentMethods(wallet.referenceId)
        return listPaymentMethods.mapTo(arrayListOf()) {
            PaymentMethodsDto.Map.from(it)
        }
    }

    override fun removeCard(customerId: UUID, paymentMethodId: String) {
        val wallet = verifyOrCreateWalletAccount(customerId)
        checkOwner(wallet.id!!, customerId)
        integrationsPaymentsAcl.removePaymentMethods(wallet.referenceId, paymentMethodId)
    }

    @Transactional
    override fun newCardTransaction(walletCardChargeDto: WalletCardChargeDto): WalletChargeResponseDto {
        val walletCustomer = walletCustomerRepository.findByCustomerId(walletCardChargeDto.customerId!!).get()
        val iuguChargeResponse = integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))
        val walletChargeResponseDto = WalletChargeResponseDto.Map.from(iuguChargeResponse)
        chargeWalletWhenCard(walletCustomer, walletCardChargeDto, walletChargeResponseDto)
        return walletChargeResponseDto
    }

    @Transactional
    override fun newBalanceDebitTransaction(walletBalanceDebitDto: WalletBalanceDebitDto) {
        val walletCustomer = walletCustomerRepository.findByCustomerId(walletBalanceDebitDto.customerId).get()
        walletStatementService.newWalletBalanceDebitTransaction(walletCustomer, walletBalanceDebitDto)
        walletCustomer.chargeWallet(walletBalanceDebitDto.bids.negate())
        walletCustomerRepository.save(walletCustomer)
    }

    private fun chargeWalletWhenCard(walletCustomer: WalletCustomer, walletCardChargeDto: WalletCardChargeDto, walletChargeResponseDto: WalletChargeResponseDto) {
        walletStatementService.newRecordCardTransaction(walletCustomer, walletCardChargeDto, walletChargeResponseDto)
        if (walletChargeResponseDto.success) {
            walletCustomer.chargeWallet(walletCardChargeDto.getBidsQuantity())
            walletCustomerRepository.save(walletCustomer)
        }
    }

    private fun verifyOrCreateWalletAccount(customerId: UUID): WalletCustomer {
        // Check if wallet exists
        val wallet = walletCustomerRepository.findByCustomerId(customerId)
        if (wallet.isPresent) return wallet.get()

        // Create customer on partner
        val customer = customersAcl.findById(customerId)
        val iuguCustomer: IuguCustomerResponse = integrationsPaymentsAcl.createCustomer(IuguCustomerRequest.Map.from(customer.get()))

        return walletCustomerRepository.save(
                WalletCustomer(
                        customer = customer.get(),
                        referenceId = iuguCustomer.id.toString()))
    }

}