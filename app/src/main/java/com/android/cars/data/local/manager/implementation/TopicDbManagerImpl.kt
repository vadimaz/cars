package com.android.cars.data.local.manager.implementation

import com.android.cars.data.local.dao.TopicDao
import com.android.cars.data.local.manager.TopicDbManager
import com.android.cars.data.local.models.topic.DbTopic
import com.android.cars.data.models.topic.Topic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TopicDbManagerImpl(private val topicDao: TopicDao) : TopicDbManager {

    override suspend fun saveTopics(topics: List<Topic>) {
        val dbTopics = topics.map { DbTopic.createFromModel(it) }
        topicDao.insertAll(dbTopics)
    }

    override fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAll().map { dbTopics ->
            dbTopics.map { dbTopic -> DbTopic.mapToModel(dbTopic) }
        }
    }

    override fun getTopicById(id: String): Flow<Topic> {
        return topicDao.getTopicById(id).map { dbTopic -> DbTopic.mapToModel(dbTopic) }
    }

    override suspend fun deleteAllTopics() {
        topicDao.deleteAll()
    }
}