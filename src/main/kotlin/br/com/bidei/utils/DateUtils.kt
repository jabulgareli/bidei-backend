package br.com.bidei.utils

import java.time.Instant
import java.util.*

object DateUtils {
    fun addMinutes(date: Date, minutes: Int) = Date(date.time - minutes * 60 * 1000)
    fun utcNow() = Date.from(Instant.now())
}