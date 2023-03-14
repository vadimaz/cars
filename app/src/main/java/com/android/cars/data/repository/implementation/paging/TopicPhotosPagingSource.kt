package com.android.cars.data.repository.implementation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.remote.manager.TopicDataSource

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class TopicPhotosPagingSource(
    private val topicDataSource: TopicDataSource,
    private val topicId: String
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val list = topicDataSource.getTopicPhotos(topicId, page, params.loadSize)
            LoadResult.Page(
                data = list,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1
            )
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int {
        return UNSPLASH_STARTING_PAGE_INDEX
    }
}
