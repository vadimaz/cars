package com.android.cars.data.repository.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.cars.data.Resource
import com.android.cars.data.local.manager.TopicDbManager
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.manager.TopicDataSource
import com.android.cars.data.remote.utils.PAGE_SIZE
import com.android.cars.data.repository.TopicRepository
import com.android.cars.data.repository.implementation.paging.TopicPhotosPagingSource
import com.android.cars.util.wrapAsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val topicDataSource: TopicDataSource,
    private val topicDbManager: TopicDbManager
) : TopicRepository {

    override fun observeLocalTopics(): Flow<List<Topic>> {
        return topicDbManager.getAllTopics()
    }

    override fun getTopics(orderBy: TopicsOrderByEnum): Flow<Resource<Unit>> = flow {
        val topics = topicDataSource.getTopics(1, 10, orderBy) // TODO: implement pagination using Paging v3
        topicDbManager.deleteAllTopics()
        topicDbManager.saveTopics(topics)
        emit(Unit)
    }.wrapAsResult()

    override fun getTopicById(id: String): Flow<Topic> {
        return topicDbManager.getTopicById(id)
    }

    override fun getTopicPhotos(topicId: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE * 2
            ),
            pagingSourceFactory = { TopicPhotosPagingSource(topicDataSource, topicId) }
        ).flow
    }
}