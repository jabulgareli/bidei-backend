package br.com.bidei.utils

import com.google.gson.reflect.TypeToken

val jsonListOfStringType = object : TypeToken<List<String>>() {}.type
val jsonMapOfStringType = object : TypeToken<Map<String, String>>() {}.type