package com.android.cars.data.local.manager

import com.android.cars.data.models.Preferences
import com.android.cars.data.models.topic.TopicsOrderByEnum
import kotlinx.coroutines.flow.Flow

interface PreferencesManager {

    fun getPreferencesFlow(): Flow<Preferences>

    suspend fun getPreferences(): Preferences

    fun isAuthorized(): Flow<Boolean>

    suspend fun setToken(accessToken: String?, refreshToken: String?)

    suspend fun updateTopicSorting(topicsOrderBy: TopicsOrderByEnum)
}