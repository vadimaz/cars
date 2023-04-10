package com.android.cars.data.local.models.media

import java.time.LocalDateTime

data class MediaContent(
    val id: String,
    val type: MediaContentType,
    val publishedAt: LocalDateTime,
    val duration: Long? = null,
    val url: String,
    val status: MediaContentStatus
)

enum class MediaContentType {
    PHOTO,
    VIDEO
}

enum class MediaContentStatus {
    AVAILABLE,
    EXPIRING_SOON,
    REQUESTED,
    QUEUED,
    REQUEST_FAILED,
    UNAVAILABLE
}
