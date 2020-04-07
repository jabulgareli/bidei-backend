package br.com.bidei.customers.domain.model

import br.com.bidei.address.domain.model.City
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.validation.constraints.NotNull

@Entity
class Customer(
        @Id
        val id: UUID,

        @NotNull
        var name: String,

        @NotNull
        val email: String,

        @NotNull
        val phone: String,

        @NotNull
        @OneToOne
        var city: City,

        @NotNull
        val referenceId: String, // External ID (Firebase)

        @NotNull
        val provider: String
) {
        fun phonePrefix() = phone.substring(0, 3)
        fun phone() = phone.substring(3, phone.length)
}