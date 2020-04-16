package br.com.bidei.factories

import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.wallet.domain.dto.*
import br.com.bidei.wallet.domain.model.WalletCustomer
import br.com.bidei.wallet.domain.model.WalletStatement
import java.math.BigDecimal
import java.util.*

object WalletFactory {

    val walletId = UUID.randomUUID()
    private val referenceId = "ABC123"
    private val paymentMethodId = "XXX999"

    fun newWalletCustomer(customer: Customer) = WalletCustomer(walletId, customer, referenceId)

    fun newCreateCardDto() =
            CreateCardDto(
                    number = "000000000000000",
                    verificationValue = "000",
                    firstName = "firstName",
                    lastName = "lastName",
                    month = "12",
                    year = "2020",
                    description = "Test card 1"
            )

    fun newWalletCardChargeDto() = WalletCardChargeDto(
            CustomerFactory.customerId,
            WalletChargeEnum.IUGU,
            paymentMethodId,
            "email",
            BigDecimal.TEN
    )

    fun newWalletStatement(walletCustomer: WalletCustomer) = WalletStatement(
            walletCustomer = walletCustomer,
            source = WalletChargeEnum.IUGU.name,
            success = true,
            message = "message",
            url = "url",
            pdf = "pdf",
            invoiceId = "invoiceId",
            transactionCode = "transactionCode",
            bids = BigDecimal.TEN
    )

    fun newWalletBalanceDebitDto(customer: Customer) = WalletBidDebitDto (
        customerId = customer.id
    )


}