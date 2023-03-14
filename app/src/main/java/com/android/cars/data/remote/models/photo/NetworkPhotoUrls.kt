package com.android.cars.data.remote.models.photo

import com.android.cars.data.models.photo.PhotoUrls
import com.google.gson.annotations.SerializedName

data class NetworkPhotoUrls(
    @SerializedName("raw") val raw: String?,
    @SerializedName("full") val full: String?,
    @SerializedName("regular") val regular: String?,
    @SerializedName("small") val small: String?,
    @SerializedName("thumb") val thumb: String?
) {
    companion object {
        fun mapToModel(networkPhotoUrls: NetworkPhotoUrls): PhotoUrls = PhotoUrls(
            raw = networkPhotoUrls.raw,
            full = networkPhotoUrls.full,
            regular = networkPhotoUrls.regular,
            small = networkPhotoUrls.small,
            thumb = networkPhotoUrls.thumb
        )
    }
}