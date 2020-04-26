package br.com.bidei.bid.application.adapters

import br.com.bidei.acl.ports.AuctionAclPort
import br.com.bidei.acl.ports.CustomersAclPort
import br.com.bidei.acl.ports.WalletAclPort
import br.com.bidei.auction.domain.dto.AuctionDto
import br.com.bidei.auction.domain.model.Auction
import br.com.bidei.auction.domain.model.AuctionProductType
import br.com.bidei.bid.domain.dto.NewBidDto
import br.com.bidei.bid.domain.ports.services.BidService
import br.com.bidei.bid.domain.dto.BidResponseDto
import br.com.bidei.bid.domain.exception.AuctionFinishedException
import br.com.bidei.bid.domain.exception.DuplicateBidException
import br.com.bidei.bid.domain.model.Bid
import br.com.bidei.bid.domain.ports.repositories.BidRepository
import br.com.bidei.bid.domain.ports.repositories.BidValueRepository
import br.com.bidei.utils.DateUtils
import br.com.bidei.wallet.domain.dto.WalletBidDebitDto
import br.com.bidei.wallet.domain.exceptions.InsufficientBalanceOnWalletException
import com.google.gson.Gson
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
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
        private val bidValueRepository: BidValueRepository,
        private val gson: Gson) : BidService {

    @Transactional
    override fun newBid(newBidDto: NewBidDto): Bid {
        val customer = customersAclPort.findById(newBidDto.customerId!!).get()

        if(!walletAclPort.hasWallet(customer.id))
            throw InsufficientBalanceOnWalletException()

        val auction = auctionAclPort.findById(newBidDto.auctionId!!)

        if(auction.isFinished())
            throw AuctionFinishedException()

        val duplicatedBid = bidRepository.findByCustomerIdAndAuctionIdAndValueAndPriceOnBidAndCreatedDateGreaterThanEqual(
                customer.id, auction.id!!, newBidDto.value!!, newBidDto.currentPrice!!, DateUtils.addMinutes(DateUtils.utcNow(), -5))

        if (duplicatedBid.isPresent)
            throw DuplicateBidException()

        walletAclPort.newBalanceDebitTransaction(WalletBidDebitDto(
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

    @Transactional
    override fun findAuctionsWithBidByCustomer(customerId: UUID, isOpen: Boolean?, pageRequest: PageRequest): Page<AuctionDto> {
        var auctions: Page<Auction>

        if (isOpen == null)
            auctions = bidRepository.findDistinctAuctionIdByCustomerId(customerId, pageRequest)
        else if (isOpen)
            auctions = bidRepository.findOnlyOpenDistinctAuctionIdByCustomerId(customerId, DateUtils.utcNow(), pageRequest)
        else
            auctions = bidRepository.findOnlyFinishedDistinctAuctionIdByCustomerId(customerId, DateUtils.utcNow(), pageRequest)

        return auctions.map{ a -> AuctionDto.Map.fromAuction(gson, a, false) }
    }

}