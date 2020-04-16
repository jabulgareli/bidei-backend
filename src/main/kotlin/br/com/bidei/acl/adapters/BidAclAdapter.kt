package br.com.bidei.acl.adapters

import br.com.bidei.acl.ports.BidAclPort
import br.com.bidei.bid.domain.repository.BidRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class BidAclAdapter(private val bidRepository: BidRepository) : BidAclPort {
    override fun countByCustomerId(customerId: UUID) =
            bidRepository.countByCustomerId(customerId)
}