package com.android.cars.data.local.models.photo

import androidx.room.Embedded
import com.android.cars.data.models.photo.CoverPhoto

data class DbCoverPhoto(
    @Embedded(prefix = COLUMN_PHOTO_URLS)
    val photoUrls: DbPhotoUrls?
) {
    companion object {
        const val COLUMN_PHOTO_URLS = "photo_urls"

        fun createFromModel(coverPhoto: CoverPhoto) = DbCoverPhoto(
            photoUrls = coverPhoto.photoUrls?.let { DbPhotoUrls.createFromModel(it) }
        )

        fun mapToModel(dbCoverPhoto: DbCoverPhoto) = CoverPhoto(
            photoUrls = dbCoverPhoto.photoUrls?.let { DbPhotoUrls.mapToModel(it) }
        )
    }
}