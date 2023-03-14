package com.android.cars.data.local.manager.implementation

import androidx.datastore.core.DataStore
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.models.Preferences
import com.android.cars.data.models.topic.TopicsOrderByEnum
import kotlinx.coroutines.flow.*

class PreferencesManagerImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {

    override fun getPreferencesFlow(): Flow<Preferences> = dataStore.data

    override suspend fun getPreferences(): Preferences = getPreferencesFlow().take(1).single()

    override fun isAuthorized(): Flow<Boolean> =
        getPreferencesFlow().map { it.accessToken != null }.distinctUntilChanged()

    override suspend fun setToken(accessToken: String?, refreshToken: String?) {
        dataStore.updateData { it.copy(accessToken = accessToken, refreshToken = refreshToken) }
    }

    override suspend fun updateTopicSorting(topicsOrderBy: TopicsOrderByEnum) {
        dataStore.updateData { it.copy(topicsOrderBy = topicsOrderBy) }
    }
}