package com.android.cars.di

import android.content.Context
import com.android.cars.data.local.AppDataBase
import com.android.cars.data.local.dao.TopicDao
import com.android.cars.data.local.datastore.preferencesDataStore
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.local.manager.TopicDbManager
import com.android.cars.data.local.manager.implementation.PreferencesManagerImpl
import com.android.cars.data.local.manager.implementation.TopicDbManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object LocalStorageModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun provideTopicsDao(dataBase: AppDataBase): TopicDao = dataBase.getTopicsDao()

    @Provides
    fun provideTopicDbManager(topicDao: TopicDao): TopicDbManager = TopicDbManagerImpl(topicDao)

    @Provides
    fun providePreferencesManager(
        @ApplicationContext context: Context
    ): PreferencesManager = PreferencesManagerImpl(context.preferencesDataStore)
}