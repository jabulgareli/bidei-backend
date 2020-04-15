package br.com.bidei.customers.domain.model

import br.com.bidei.address.domain.model.City
import br.com.bidei.utils.DateUtils
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
        @OneToOne
        @NotNull
        var city: City,
        @NotNull
        val referenceId: String, // External ID (Firebase)
        @NotNull
        val provider: String,
        @OneToOne
        var partner: Customer? = null,
        var partnerReferencedAt: Date? = null
) {
        fun phonePrefix() = phone.substring(0, 3)
        fun phone() = phone.substring(3, phone.length)

        fun receiveInviteFromPartner(partner: Customer){
                if (partner.id == this.id)
                        throw Exception("A customer can`t be refer to himself")

                this.partner = partner
                this.partnerReferencedAt = DateUtils.utcNow()
        }
}