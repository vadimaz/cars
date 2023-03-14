package com.android.cars.data.remote.adapters

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LocalDateTimeTypeAdapter @Inject constructor() : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private fun toString(date: LocalDateTime): String {
        return date.atOffset(ZoneOffset.UTC).toString()
    }

    private fun fromString(date: String): LocalDateTime {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == null) JsonNull.INSTANCE else JsonPrimitive(
            toString(src)
        )
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        return if (json == null || json == JsonNull.INSTANCE) {
            null
        } else {
            fromString(json.asString)
        }
    }
}
