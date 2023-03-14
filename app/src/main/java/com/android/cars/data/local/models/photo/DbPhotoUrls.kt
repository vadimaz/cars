package com.android.cars.data.local.models.photo

import com.android.cars.data.models.photo.PhotoUrls

data class DbPhotoUrls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
) {
    companion object {
        fun createFromModel(photoUrls: PhotoUrls): DbPhotoUrls = DbPhotoUrls(
            raw = photoUrls.raw,
            full = photoUrls.full,
            regular = photoUrls.regular,
            small = photoUrls.small,
            thumb = photoUrls.thumb
        )

        fun mapToModel(dbPhotoUrls: DbPhotoUrls): PhotoUrls = PhotoUrls(
            raw = dbPhotoUrls.raw,
            full = dbPhotoUrls.full,
            regular = dbPhotoUrls.regular,
            small = dbPhotoUrls.small,
            thumb = dbPhotoUrls.thumb
        )
    }
}