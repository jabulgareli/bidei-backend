package br.com.bidei.utils

import okhttp3.internal.format
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object DateUtils {
    fun addMinutes(date: Date, minutes: Int) = Date(date.time - minutes * 60 * 1000)

    fun utcNow() = Date.from(Instant.now())

    fun dateWithoutTime(date: Date): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(format.format(date))
    }

    fun dateWithoutTime(date: Timestamp): Timestamp {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return Timestamp(formatter.parse(formatter.format(date)).time)
    }
}