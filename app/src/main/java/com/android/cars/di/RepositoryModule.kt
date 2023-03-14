package com.android.cars.di

import com.android.cars.data.repository.AuthRepository
import com.android.cars.data.repository.PhotoRepository
import com.android.cars.data.repository.TopicRepository
import com.android.cars.data.repository.implementation.AuthRepositoryImpl
import com.android.cars.data.repository.implementation.PhotoRepositoryImpl
import com.android.cars.data.repository.implementation.TopicRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindTopicRepositoryImpl(impl: TopicRepositoryImpl): TopicRepository

    @Binds
    fun bindPhotoRepositoryImpl(impl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun bindAuthRepositoryImpl(impl: AuthRepositoryImpl): AuthRepository
}