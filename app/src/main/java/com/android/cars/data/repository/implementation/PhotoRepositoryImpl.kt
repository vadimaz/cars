package com.android.cars.data.repository.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.remote.manager.PhotoDataSource
import com.android.cars.data.remote.utils.PAGE_SIZE
import com.android.cars.data.repository.PhotoRepository
import com.android.cars.data.repository.implementation.paging.PhotoPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PhotoRepositoryImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : PhotoRepository {

    override fun getPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE * 2
            ),
            pagingSourceFactory = { PhotoPagingSource(photoDataSource) }
        ).flow
    }
}