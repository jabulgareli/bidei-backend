package br.com.bidei.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


object DateUtils {
    fun addMinutes(date: Timestamp, minutes: Int): Timestamp {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE, minutes)
        return Timestamp(cal.time.time)
    }

    fun addDays(date: Timestamp, days: Int): Timestamp {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DAY_OF_YEAR, days)
        return Timestamp(cal.time.time)
    }

    fun getCurrentYear(): Int {
        val cal = Calendar.getInstance()
        cal.time = utcNow()
       return cal.get(Calendar.YEAR)
    }

    fun utcNow(): Timestamp {
        val date = LocalDateTime.now(ZoneOffset.UTC)
        return Timestamp.valueOf(date)
    }

    fun dateWithoutTime(date: Timestamp): Timestamp {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return Timestamp(formatter.parse(formatter.format(date)).time)
    }
}