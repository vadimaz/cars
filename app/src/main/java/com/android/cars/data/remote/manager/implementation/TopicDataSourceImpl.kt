package com.android.cars.data.remote.manager.implementation

import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.api.TopicApi
import com.android.cars.data.remote.manager.TopicDataSource
import com.android.cars.data.remote.models.photo.NetworkPhoto
import com.android.cars.data.remote.models.topic.NetworkTopic
import com.android.cars.data.remote.utils.NetworkErrorConverterHelper

class TopicDataSourceImpl(
    private val topicApi: TopicApi,
    private val networkErrorConverterHelper: NetworkErrorConverterHelper
) : TopicDataSource {

    override suspend fun getTopics(
        page: Int,
        perPage: Int,
        orderBy: TopicsOrderByEnum
    ): List<Topic> = try {
        val data = topicApi.getTopics(page, perPage, orderBy)
        data.map { NetworkTopic.mapToModel(it) }
    } catch (e: Throwable) {
        throw networkErrorConverterHelper.parseError(e)
    }

    override suspend fun getTopicPhotos(topicId: String, page: Int, perPage: Int): List<Photo> =
        try {
            val data = topicApi.getTopicPhotos(topicId, page, perPage)
            data.map { NetworkPhoto.mapToModel(it) }
        } catch (e: Throwable) {
            throw networkErrorConverterHelper.parseError(e)
        }
}