package br.com.bidei.acl.adapters

import br.com.bidei.customers.domain.ports.services.CustomersService
import br.com.bidei.customers.domain.model.Customer
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.coupon.domain.model.Coupon
import br.com.bidei.customers.domain.ports.repositories.CustomersRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomersAclAdapter(private val customersService: CustomersService,
                          private val customersRepository: CustomersRepository) : CustomersAclPort {

    override fun findById(id: UUID): Optional<Customer> {
        return customersService.findById(id)
    }

    override fun receiveInviteFromPartner(customer: Customer, coupon: Coupon){
        customer.receiveInviteFromPartner(coupon)
        customersRepository.save(customer)
    }

}