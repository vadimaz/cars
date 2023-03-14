package com.android.cars.data.repository

import androidx.paging.PagingData
import com.android.cars.data.Resource
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    fun observeLocalTopics(): Flow<List<Topic>>

    fun getTopics(orderBy: TopicsOrderByEnum): Flow<Resource<Unit>>

    fun getTopicById(id: String): Flow<Topic>

    fun getTopicPhotos(topicId: String): Flow<PagingData<Photo>>
}