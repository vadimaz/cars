package com.android.cars.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.android.cars.data.models.Preferences
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream

private const val PREFERENCES_FILE_NAME = "preferences.pb"

val Context.preferencesDataStore: DataStore<Preferences> by dataStore(
    fileName = PREFERENCES_FILE_NAME,
    serializer = PreferencesSerializer
)

private object PreferencesSerializer : Serializer<Preferences> {
    override val defaultValue: Preferences
        get() = Preferences()

    override suspend fun readFrom(input: InputStream): Preferences {
        return ProtoBuf.decodeFromByteArray(input.readBytes())
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: Preferences, output: OutputStream) {
        output.write(ProtoBuf.encodeToByteArray(t))
    }
}