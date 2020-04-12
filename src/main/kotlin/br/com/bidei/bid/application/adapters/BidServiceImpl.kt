package br.com.bidei.bid.application.adapters

import br.com.bidei.acl.ports.AuctionAclPort
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.application.dto.NewBidDto
import br.com.bidei.bid.application.ports.BidService
import br.com.bidei.bid.domain.dto.BidResponseDto
import br.com.bidei.bid.domain.exception.AuctionFinishedException
import br.com.bidei.bid.domain.exception.DuplicateBidException
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.model.BidValue
import br.com.bidei.bid.domain.repository.BidRepository
import br.com.bidei.bid.domain.repository.BidValueRepository
import br.com.bidei.utils.DateUtils
import br.com.bidei.wallet.domain.dto.WalletBalanceDebitDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BidServiceImpl(
        private val bidRepository: BidRepository,
        private val auctionAclPort: AuctionAclPort,
        private val customersAclPort: CustomersAclPort,
        private val walletAclPort: WalletAclPort,
        private val bidValueRepository: BidValueRepository) : BidService {

    @Transactional
    override fun newBid(newBidDto: NewBidDto): Bid {
        val customer = customersAclPort.findById(newBidDto.customerId!!).get()
        val auction = auctionAclPort.findById(newBidDto.auctionId!!)

        if(auction.isFinished())
            throw AuctionFinishedException()

        val duplicatedBid = bidRepository.findByCustomerIdAndAuctionIdAndValueAndPriceOnBidAndCreatedDateGreaterThanEqual(
                customer.id, auction.id!!, newBidDto.value!!, newBidDto.currentPrice!!, DateUtils.addMinutes(DateUtils.utcNow(), -5))

        if (duplicatedBid.isPresent)
            throw DuplicateBidException()

        walletAclPort.newBalanceDebitTransaction(WalletBalanceDebitDto(
                customerId = customer.id
        ))

        auctionAclPort.acceptBid(auction, newBidDto.value, newBidDto.currentPrice)

        return bidRepository.save(
                Bid(auction = auction,
                        customer = customer,
                        value = newBidDto.value,
                        priceOnBid = newBidDto.currentPrice,
                        createdDate = DateUtils.utcNow()))

    }

    override fun listBidValues(productType: AuctionProductType) =
            bidValueRepository.findAllByProductType(productType)

    @Transactional
    override fun findByCustomerIdAndAuctionId(customerId: UUID, auctionId: UUID, pageable: Pageable) =
            bidRepository.findByCustomerIdAndAuctionId(customerId, auctionId, pageable)
                    .map{t -> BidResponseDto.Map.fromBid (t)}

    @Transactional
    override fun findByAuctionId(auctionId: UUID, pageable: Pageable) =
            bidRepository.findByAuctionId(auctionId, pageable)
                    .map{t -> BidResponseDto.Map.fromBid (t)}
}