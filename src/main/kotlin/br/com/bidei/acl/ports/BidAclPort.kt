package br.com.bidei.acl.ports

import java.util.*

interface BidAclPort {
    fun countByCustomerId(customerId: UUID): Int
}