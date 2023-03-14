package com.android.cars.data.remote.manager

import com.android.cars.data.models.photo.Photo

interface PhotoDataSource {

    suspend fun getPhotos(page: Int, perPage: Int): List<Photo>
}