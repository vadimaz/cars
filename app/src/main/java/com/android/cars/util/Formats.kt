package com.android.cars.util

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object Formats {
    const val DATE_FORMAT_FULL = "dd-MM-yyyy HH:mm:ss"

    private const val DECIMAL_SCALE = 2

    @JvmStatic
    fun formatDecimalValue(decimalValue: Double): String = formatDecimalValue(decimalValue, null)

    @JvmStatic
    fun formatDecimalValue(
        decimalValue: Double,
        decimalScale: Int? = null,
        stripTrailingZeros: Boolean = false
    ): String {
        val bigDecimal = BigDecimal(decimalValue)
            .setScale(decimalScale ?: DECIMAL_SCALE, RoundingMode.HALF_UP)
        return if (stripTrailingZeros) {
            bigDecimal.stripTrailingZeros()
        } else {
            bigDecimal
        }.toPlainString()
    }

    @JvmStatic
    fun dateFormat(date: LocalDateTime): String = dateFormat(date, null)

    @JvmStatic
    fun dateFormat(date: LocalDateTime, dateFormat: String?): String  {
        val zonedDateTime = ZonedDateTime.ofInstant(date, ZoneOffset.UTC, ZoneId.systemDefault())
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat ?: DATE_FORMAT_FULL)
        return zonedDateTime.format(dateTimeFormatter)
    }

    fun fromHtml(text: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(
                text,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(text)
        }
    }

    fun removeHtmlTags(text: String): String {
        return fromHtml(text).toString()
    }
}