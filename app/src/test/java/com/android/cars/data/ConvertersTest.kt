package com.android.cars.data

import com.android.cars.data.local.utils.Converters
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

class ConvertersTest {

    private val dateTime = LocalDateTime.now()

    @Test fun calendarToDatestamp() {
        assertEquals(getMillis(dateTime), Converters().dateToTimestamp(dateTime))
    }

    @Test fun datestampToCalendar() {
        assertEquals(getMillis(Converters().timestampToDate(getMillis(dateTime))), getMillis(dateTime))
    }

    private fun getMillis(dateTime: LocalDateTime): Long = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
}
