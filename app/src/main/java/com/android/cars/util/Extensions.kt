package com.android.cars.util

import com.android.cars.data.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

inline val <reified T> T.TAG: String
    get() = T::class.java.name

inline fun <reified T> Flow<T>.wrapAsResult(): Flow<Resource<T>> {
    return this.map { Resource.Success(it) as Resource<T> }
        .onStart { emit(Resource.Loading) }
        .catch { error ->
            Timber.e(error)
            emit(Resource.Error(error))
        }
}

val Job?.isActive: Boolean
    get() = this?.isActive == true