package br.com.bidei.address.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class State(
        @Id
        val id: Long,

        @JsonIgnore
        val state: String,

        val initials: String,

        @JsonIgnore
        val region: String
)