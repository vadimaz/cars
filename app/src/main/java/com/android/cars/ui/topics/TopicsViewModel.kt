package com.android.cars.ui.topics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cars.data.Resource
import com.android.cars.data.initial
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.repository.TopicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
    private val topicRepository: TopicRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private var jobFetchTopics: Job? = null
    private val _topicsResponse = MutableStateFlow<Resource<Unit>>(Resource.Initial)
    val topicsResponse = _topicsResponse.asStateFlow()

    val topics = topicRepository.observeLocalTopics()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val topicsOrder = preferencesManager.getPreferencesFlow().map { it.topicsOrderBy }
        .stateIn(viewModelScope, SharingStarted.Eagerly, TopicsOrderByEnum.POSITION)

    init {
        if (topicsResponse.value.initial) {
            loadTopics()
        }
    }

    fun loadTopics() {
        jobFetchTopics?.cancel()
        jobFetchTopics = viewModelScope.launch {
            topicRepository.getTopics(topicsOrder.value).collect {
                _topicsResponse.emit(it)
            }
        }
    }

    fun updateTopicSorting(topicsOrderBy: TopicsOrderByEnum) {
        viewModelScope.launch {
            preferencesManager.updateTopicSorting(topicsOrderBy)
            loadTopics()
        }
    }
}