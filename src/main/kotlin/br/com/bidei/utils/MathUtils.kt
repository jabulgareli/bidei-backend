package br.com.bidei.utils

import java.math.BigDecimal
import java.util.*

object MathUtils {
    fun round(value: BigDecimal, decimals: Int): BigDecimal{
        val format = "%.${decimals}f"
        val result = format.format(Locale.US, value)
        return BigDecimal(result)
    }
}