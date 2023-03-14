package com.android.cars.data.local.manager

import com.android.cars.data.models.topic.Topic
import kotlinx.coroutines.flow.Flow

interface TopicDbManager {
    suspend fun saveTopics(topics: List<Topic>)

    fun getAllTopics(): Flow<List<Topic>>

    fun getTopicById(id: String): Flow<Topic>

    suspend fun deleteAllTopics()
}