package com.android.cars.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.cars.data.local.AppDataBase
import com.android.cars.data.local.manager.TopicDbManager
import com.android.cars.data.local.manager.implementation.TopicDbManagerImpl
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.remote.manager.TopicDataSource
import com.android.cars.data.remote.manager.implementation.TopicDataSourceImpl
import com.android.cars.data.remote.utils.NetworkErrorConverterHelper
import com.android.cars.data.repository.implementation.TopicRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TopicRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDataBase: AppDataBase
    private lateinit var topicDbManager: TopicDbManager
    private lateinit var topicDataSource: TopicDataSource

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDataBase = AppDataBase.getTestInstance(context)
        topicDbManager = TopicDbManagerImpl(appDataBase.getTopicsDao())
        topicDataSource = TopicDataSourceImpl(
            TopicApiFake(),
            NetworkErrorConverterHelper(context, Gson())
        )
    }

    @After
    fun tearDown() {
        appDataBase.close()
    }

    @Test
    fun canFetchAndSaveTopics() = runBlockingTest {
        val underTest = TopicRepositoryImpl(topicDataSource, topicDbManager)
        val results = mutableListOf<List<Topic>>()

        val observeTopicsJob = launch {
            underTest.observeLocalTopics().collect { results.add(it) }
        }

        assertThat(results.size, equalTo(1))
        // initial size of topics from db (results[0]) should be empty
        assertThat(results[0], empty())

        // fetch topics from fake api
        val fetchTopicsJob = launch {
            underTest.getTopics(TopicsOrderByEnum.POSITION).collect()
        }

        // fetched topics (results[1]) should not be empty
        assertThat(results[1].size, equalTo(TopicApiFake.TOPIC_LIST_SIZE))

        observeTopicsJob.cancel()
        fetchTopicsJob.cancel()
    }
}
