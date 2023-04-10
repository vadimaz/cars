package com.android.cars.data.repository

import com.android.cars.data.local.models.media.MediaContent

interface MediaLibraryRepository {
    fun getMedia(): List<MediaContent>
}