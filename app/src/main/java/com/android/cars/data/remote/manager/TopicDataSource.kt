package com.android.cars.data.remote.manager

import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum

interface TopicDataSource {

    suspend fun getTopics(page: Int, perPage: Int, orderBy: TopicsOrderByEnum): List<Topic>

    suspend fun getTopicPhotos(topicId: String, page: Int, perPage: Int): List<Photo>
}