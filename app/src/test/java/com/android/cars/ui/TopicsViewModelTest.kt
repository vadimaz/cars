package com.android.cars.ui

import com.android.cars.MainDispatcherRule
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.repository.TopicRepository
import com.android.cars.ui.topics.TopicsViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
class TopicsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(paused = true)

    private val testTopics = listOf(
        Topic("0", "Topic #1", "Body...", LocalDateTime.now(), null),
        Topic("1", "Topic #2", "Body...", LocalDateTime.now(), null)
    )

    private val repo: TopicRepository = mock {
        on { observeLocalTopics() } doReturn flowOf(testTopics)
    }

    private val preferences: PreferencesManager = mock {

    }

    private lateinit var underTest: TopicsViewModel

    @Before
    fun setUp() {
        underTest = TopicsViewModel(repo, preferences)
    }

    @Test
    fun `initial state`() {
        mainDispatcherRule.dispatcher.runCurrent()

        runBlocking {
            assertArrayEquals(testTopics.toTypedArray(), underTest.topics.value.toTypedArray())
        }
    }
}
