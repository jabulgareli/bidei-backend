package br.com.bidei.utils

import br.com.bidei.auction.domain.dto.AuctionCarOptionDto
import com.google.gson.reflect.TypeToken

val jsonListOfStringType = object : TypeToken<List<String>>() {}.type
val jsonMapOfStringType = object : TypeToken<Map<String, String>>() {}.type
val jsonListOfAuctionCarOption = object : TypeToken<ArrayList<AuctionCarOptionDto>>() {}.type