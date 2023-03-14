package com.android.cars.ui.topic_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.repository.TopicRepository
import com.android.cars.util.isActive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicDetailsViewModel @Inject constructor(
    private val topicRepository: TopicRepository
) : ViewModel() {

    private val _topic = MutableStateFlow<Topic?>(null)
    val topic = _topic.asStateFlow()

    private var jobFetchPhotos: Job? = null
    private val _photosResponse = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
    val photosResponse = _photosResponse.asStateFlow()

    fun getTopicById(id: String) {
        viewModelScope.launch {
            topicRepository.getTopicById(id).collect {
                _topic.emit(it)
            }
        }
    }

    fun loadPhotos(topicId: String) {
        if (jobFetchPhotos.isActive) return
        jobFetchPhotos = viewModelScope.launch {
            topicRepository.getTopicPhotos(topicId)
                .cachedIn(viewModelScope)
                .collect {
                    _photosResponse.emit(it)
                }
        }
    }
}