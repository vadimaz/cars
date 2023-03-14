package com.android.cars.data.remote.models.photo

import com.android.cars.data.models.photo.Photo
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class NetworkPhoto(
    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("created_at")
    val createdAt: LocalDateTime,

    @SerializedName("color")
    val color: String?,

    @SerializedName("urls")
    val photoUrls: NetworkPhotoUrls,

    @SerializedName("width")
    val width: Int?,

    @SerializedName("height")
    val height: Int?
) {
    companion object {
        fun mapToModel(networkPhoto: NetworkPhoto) = Photo(
            id = networkPhoto.id,
            description = networkPhoto.description,
            createdAt = networkPhoto.createdAt,
            color = networkPhoto.color,
            photoUrls = NetworkPhotoUrls.mapToModel(networkPhoto.photoUrls),
            width = networkPhoto.width,
            height = networkPhoto.height
        )
    }
}