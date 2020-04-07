package br.com.bidei.wallet.application.adapters

import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.acl.ports.IntegrationsPaymentsAclPort
import br.com.bidei.factories.CustomerFactory
import br.com.bidei.factories.IuguFactory
import br.com.bidei.factories.WalletFactory
import br.com.bidei.integrations.payments.infrastructure.config.IuguConfig
import br.com.bidei.integrations.payments.infrastructure.dto.IuguChargeRequest
import br.com.bidei.wallet.application.ports.WalletService
import br.com.bidei.wallet.application.ports.WalletStatementService
import br.com.bidei.wallet.domain.dto.WalletChargeResponseDto
import br.com.bidei.wallet.domain.exceptions.InsufficientBalanceOnWalletException
import br.com.bidei.wallet.domain.ports.repository.WalletCustomerRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
internal class WalletServiceImplTest(
        @Autowired private val walletService: WalletService
) {

    @MockBean
    lateinit var iuguConfig: IuguConfig

    @MockBean
    lateinit var walletCustomerRepository: WalletCustomerRepository

    @MockBean
    lateinit var integrationsPaymentsAcl: IntegrationsPaymentsAclPort

    @MockBean
    lateinit var walletStatementService: WalletStatementService

    @Test
    fun `Valid data must add card`() {
        given(walletCustomerRepository.findByCustomerId(CustomerFactory.customerId)).willReturn(Optional.of(WalletFactory.newWalletCustomer(CustomerFactory.newCustomer())))
        given(integrationsPaymentsAcl.createPaymentToken(IuguFactory.newIuguPaymentTokenRequest(iuguConfig))).willReturn(IuguFactory.newIuguPaymentTokenResponse())
        given(integrationsPaymentsAcl.createPaymentMethods(IuguFactory.customerId.toString(), IuguFactory.newIuguPaymentMethodsRequest())).willReturn(true)
        val createCardDto = WalletFactory.newCreateCardDto()
        createCardDto.customerId = CustomerFactory.customerId
        walletService.addCard(createCardDto)

        then(walletCustomerRepository).should().findByCustomerId(CustomerFactory.customerId)
        then(integrationsPaymentsAcl).should().createPaymentToken(IuguFactory.newIuguPaymentTokenRequest(iuguConfig))
    }

    @Test
    fun `Valid data must remove card`() {
        val wallet = WalletFactory.newWalletCustomer(CustomerFactory.newCustomer())
        given(walletCustomerRepository.findByCustomerId(IuguFactory.customerId)).willReturn(Optional.of(wallet))
        given(walletCustomerRepository.findByIdAndCustomerId(wallet.id!!, wallet.customer.id)).willReturn(Optional.of(wallet))

        walletService.removeCard(IuguFactory.customerId, "paymentMethodId")
        then(walletCustomerRepository).should().findByCustomerId(IuguFactory.customerId)
    }

    @Test
    fun `When new card trasanction should return dto`() {
        val walletCustomer = WalletFactory.newWalletCustomer(CustomerFactory.newCustomer())
        val walletCardChargeDto = WalletFactory.newWalletCardChargeDto()
        val iuguChargeResponse = IuguFactory.newIuguChargeResponse(true)
        val walletChargeResponseDto = WalletChargeResponseDto.Map.from(iuguChargeResponse)

        given(walletCustomerRepository.findByCustomerId(walletCardChargeDto.customerId!!)).willReturn(Optional.of(walletCustomer))
        given(integrationsPaymentsAcl.charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))).willReturn(iuguChargeResponse)
        doNothing().`when`(walletStatementService).newRecordCardTransaction(walletCustomer,walletCardChargeDto, walletChargeResponseDto)
        given(walletCustomerRepository.save(walletCustomer)).willReturn(walletCustomer)
        walletService.newCardTransaction(walletCardChargeDto)

        then(walletCustomerRepository).should().findByCustomerId(walletCardChargeDto.customerId!!)
        then(integrationsPaymentsAcl).should().charge(IuguChargeRequest.Map.from(walletCustomer, walletCardChargeDto))
        then(walletStatementService).should().newRecordCardTransaction(walletCustomer,walletCardChargeDto, walletChargeResponseDto)
        then(walletCustomerRepository).should().save(walletCustomer)
    }

    @Test
    fun `When new balance transaction should debit bids in wallet`(){
        val walletCustomer = WalletFactory.newWalletCustomer(CustomerFactory.newCustomer())
        val walletBalanceDebitDto = WalletFactory.newWalletBalanceDebitDto(walletCustomer.customer)

        given(walletCustomerRepository.findByCustomerId(walletCustomer.customer.id)).willReturn(Optional.of(walletCustomer))
        given(walletCustomerRepository.save(walletCustomer)).willReturn(walletCustomer)
        walletCustomer.chargeWallet(BigDecimal.valueOf(10))

        walletService.newBalanceDebitTransaction(walletBalanceDebitDto)

        then(walletCustomerRepository).should().save(walletCustomer)
        then(walletStatementService).should().newWalletBalanceDebitTransaction(walletCustomer, walletBalanceDebitDto)

        assertEquals(walletCustomer.bids, BigDecimal.valueOf(9))
    }

    @Test
    fun `When new balance transaction without sufficient amount throws exception`(){
        val walletCustomer = WalletFactory.newWalletCustomer(CustomerFactory.newCustomer())
        val walletBalanceDebitDto = WalletFactory.newWalletBalanceDebitDto(walletCustomer.customer)

        given(walletCustomerRepository.findByCustomerId(walletCustomer.customer.id)).willReturn(Optional.of(walletCustomer))
        given(walletCustomerRepository.save(walletCustomer)).willReturn(walletCustomer)

        try {
            walletService.newBalanceDebitTransaction(walletBalanceDebitDto)
            fail("InsufficientBalanceOnWalletException expected")
        }catch (e: InsufficientBalanceOnWalletException){

        }catch (e: Exception){
            fail("InsufficientBalanceOnWalletException expected")
        }

        then(walletStatementService).should().newWalletBalanceDebitTransaction(walletCustomer, walletBalanceDebitDto)
    }

}