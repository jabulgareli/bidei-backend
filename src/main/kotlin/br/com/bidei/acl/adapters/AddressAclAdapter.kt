package br.com.bidei.acl.adapters

import br.com.bidei.address.domain.ports.repositories.CitiesRepository
import br.com.bidei.acl.ports.AddressAclPort
import org.springframework.stereotype.Service

@Service
class AddressAclAdapter(
        private val citiesRepository: CitiesRepository
) : AddressAclPort {

    override fun findCityById(id: Long) = citiesRepository.findById(id)

}