package br.com.bidei.wallet.domain.model

import br.com.bidei.factories.CustomerFactory
import br.com.bidei.factories.WalletFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class WalletCustomerTest {

    @Test
    fun `When chargeWallet should sum bids`() {
        val addBids = BigDecimal.TEN
        val customer = CustomerFactory.newCustomer()
        val walletCustomer = WalletFactory.newWalletCustomer(customer)
        assertEquals(walletCustomer.bids, BigDecimal.ZERO)

        walletCustomer.chargeWallet(addBids)
        assertEquals(walletCustomer.bids, addBids)
    }

}