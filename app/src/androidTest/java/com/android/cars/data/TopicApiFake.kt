package com.android.cars.data

import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.api.TopicApi
import com.android.cars.data.remote.models.photo.NetworkPhoto
import com.android.cars.data.remote.models.topic.NetworkTopic
import java.time.LocalDateTime

class TopicApiFake : TopicApi {

    override suspend fun getTopicPhotos(
        topicId: String,
        page: Int,
        perPage: Int
    ): List<NetworkPhoto> {
        return emptyList()
    }

    override suspend fun getTopics(
        page: Int,
        perPage: Int,
        orderBy: TopicsOrderByEnum
    ): List<NetworkTopic> = mutableListOf<NetworkTopic>().apply {
        for (i in 0 until TOPIC_LIST_SIZE) {
            add(NetworkTopic(i.toString(), "Topic #$i", "Body...", LocalDateTime.now(), null))
        }
    }

    companion object {
        const val TOPIC_LIST_SIZE = 3
    }
}