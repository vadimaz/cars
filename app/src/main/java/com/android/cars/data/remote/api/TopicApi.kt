package com.android.cars.data.remote.api

import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.models.photo.NetworkPhoto
import com.android.cars.data.remote.models.topic.NetworkTopic
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TopicApi {

    @GET("topics")
    suspend fun getTopics(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: TopicsOrderByEnum
    ): List<NetworkTopic>

    @GET("topics/{id}/photos")
    suspend fun getTopicPhotos(
        @Path("id") topicId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<NetworkPhoto>
}