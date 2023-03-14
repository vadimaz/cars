package com.android.cars.data.local.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime): Long = date.toInstant(ZoneOffset.UTC).toEpochMilli()

    @TypeConverter
    fun timestampToDate(value: Long): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)
}
