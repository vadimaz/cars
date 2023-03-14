package com.android.cars.data.remote.models.photo

import com.android.cars.data.models.photo.CoverPhoto
import com.google.gson.annotations.SerializedName

data class NetworkCoverPhoto(
    @SerializedName("urls")
    val photoUrls: NetworkPhotoUrls?
) {
    companion object {
        fun mapToModel(networkCoverPhoto: NetworkCoverPhoto) = CoverPhoto(
            photoUrls = networkCoverPhoto.photoUrls?.let { NetworkPhotoUrls.mapToModel(it) }
        )
    }
}