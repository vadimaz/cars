package com.android.cars.data.remote.adapters

import com.android.cars.data.remote.utils.OauthGrantType
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class OauthGrantTypeAdapter @Inject constructor() : JsonSerializer<OauthGrantType>,
    JsonDeserializer<OauthGrantType> {

    override fun serialize(
        src: OauthGrantType?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src == null) {
            JsonNull.INSTANCE
        } else {
            JsonPrimitive(src.name.lowercase(Locale.ENGLISH))
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): OauthGrantType? {
        return if (json == null || json == JsonNull.INSTANCE) {
            null
        } else {
            val status = json.asString
            OauthGrantType.values().firstOrNull { it.name.lowercase(Locale.ENGLISH) == status }
        }
    }
}