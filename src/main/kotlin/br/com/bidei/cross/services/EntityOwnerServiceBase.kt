package br.com.bidei.cross.services

import br.com.bidei.cross.exceptions.EntityNotOwnedByCustomer
import br.com.bidei.cross.repository.EntityOwnerRepositoryBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
abstract class EntityOwnerServiceBase<T, U> {
    @Autowired
    lateinit var repository: EntityOwnerRepositoryBase<T, U>

    fun checkOwner(id: UUID, customerId: UUID){
        repository.findByIdAndCustomerId(id, customerId).orElse(null) ?: throw EntityNotOwnedByCustomer()
    }
}