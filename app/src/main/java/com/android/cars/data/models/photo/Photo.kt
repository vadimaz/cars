package com.android.cars.data.models.photo

import java.time.LocalDateTime

data class Photo(
    val id: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val color: String?,
    val photoUrls: PhotoUrls,
    val width: Int?,
    val height: Int?
)