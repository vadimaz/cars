package com.android.cars.ui.topics

import android.content.Context
import com.android.cars.data.TopicApiFake
import com.android.cars.data.local.AppDataBase
import com.android.cars.data.local.manager.implementation.TopicDbManagerImpl
import com.android.cars.data.remote.manager.implementation.TopicDataSourceImpl
import com.android.cars.data.remote.utils.NetworkErrorConverterHelper
import com.android.cars.data.repository.AuthRepository
import com.android.cars.data.repository.PhotoRepository
import com.android.cars.data.repository.TopicRepository
import com.android.cars.data.repository.implementation.AuthRepositoryImpl
import com.android.cars.data.repository.implementation.PhotoRepositoryImpl
import com.android.cars.data.repository.implementation.TopicRepositoryImpl
import com.android.cars.di.RepositoryModule
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import org.junit.Test

// UI test with Hilt https://developer.android.com/training/dependency-injection/hilt-testing#ui-test
@HiltAndroidTest
@UninstallModules(RepositoryModule::class)
class TopicsScreenTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class TestRepositoryModule {

        companion object {
            // provide implementation of topic repository with fake topics API
            @Provides
            fun bindTopicRepositoryImpl(@ApplicationContext context: Context): TopicRepository =
                TopicRepositoryImpl(
                    TopicDataSourceImpl(
                        TopicApiFake(),
                        NetworkErrorConverterHelper(context, Gson())
                    ),
                    TopicDbManagerImpl(AppDataBase.getTestInstance(context).getTopicsDao())
                )
        }

        @Binds
        abstract fun bindPhotoRepositoryImpl(impl: PhotoRepositoryImpl): PhotoRepository

        @Binds
        abstract fun bindAuthRepositoryImpl(impl: AuthRepositoryImpl): AuthRepository
    }

    @Test
    fun testTopicListContents() { // TODO: adapt for Compose
        /*ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.topics_recycler_view)).check(
            matches(hasChildCount(TopicApiFake.TOPIC_LIST_SIZE))
        )*/
    }
}