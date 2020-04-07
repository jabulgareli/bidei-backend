package br.com.bidei.factories

import br.com.bidei.wallet.domain.model.WalletCustomer
import java.util.*

object WalletCustomerFactory {

    val walletId = UUID.randomUUID()
    val referenceId = "765E6785A8964B3DB8F430B036F78CB0"

    fun newWalletCustomer() = WalletCustomer(
            walletId,
            CustomerFactory.newCustomer(),
            referenceId
    )

}