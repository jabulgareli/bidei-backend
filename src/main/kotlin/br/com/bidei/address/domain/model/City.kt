package br.com.bidei.address.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
data class City(
        @Id
        val id: Long,

        val name: String,

        @OneToOne
        val state: State
)