package com.android.cars.data.remote.models.topic

import com.android.cars.data.models.topic.Topic
import com.android.cars.data.remote.models.photo.NetworkCoverPhoto
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NetworkTopic(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("published_at")
    val publishedAt: LocalDateTime,

    @SerializedName("cover_photo")
    val coverPhoto: NetworkCoverPhoto?
) {
    companion object {
        fun mapToModel(networkTopic: NetworkTopic) = Topic(
            id = networkTopic.id,
            title = networkTopic.title,
            description = networkTopic.description,
            publishedAt = networkTopic.publishedAt,
            coverPhoto = networkTopic.coverPhoto?.let { NetworkCoverPhoto.mapToModel(it) }
        )
    }
}