package com.android.cars.data.repository

import androidx.paging.PagingData
import com.android.cars.data.models.photo.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getPhotos(): Flow<PagingData<Photo>>
}