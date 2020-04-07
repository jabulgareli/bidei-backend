package br.com.bidei.acl.ports

import br.com.bidei.address.domain.model.City
import java.util.*

interface AddressAclPort {
    fun findCityById(id: Long): Optional<City>
}