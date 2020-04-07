package br.com.bidei.factories

import br.com.bidei.customers.domain.model.Customer
import java.util.*

object CustomerFactory {
    val customerId = UUID.fromString("4d434e52-df5e-4ddc-a6d0-9da44fad4cb0")
    val referenceId = "AAABBBCCC555666777"
    val email = "email@email.com.br"
    val provider = "BIDEI"

    fun newCustomer() = Customer(
            customerId,
            "Name",
            email,
            "016000001111",
            CityFactory.newCity(),
            referenceId,
            provider
    )

}