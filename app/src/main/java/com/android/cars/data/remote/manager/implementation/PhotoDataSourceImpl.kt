package com.android.cars.data.remote.manager.implementation

import com.android.cars.data.models.photo.Photo
import com.android.cars.data.remote.api.PhotoApi
import com.android.cars.data.remote.manager.PhotoDataSource
import com.android.cars.data.remote.models.photo.NetworkPhoto
import com.android.cars.data.remote.utils.NetworkErrorConverterHelper

class PhotoDataSourceImpl(
    private val photoApi: PhotoApi,
    private val networkErrorConverterHelper: NetworkErrorConverterHelper
) : PhotoDataSource {

    override suspend fun getPhotos(page: Int, perPage: Int): List<Photo> = try {
        val data = photoApi.getPhotos(page, perPage)
        data.map { NetworkPhoto.mapToModel(it) }
    } catch (e: Throwable) {
        throw networkErrorConverterHelper.parseError(e)
    }
}