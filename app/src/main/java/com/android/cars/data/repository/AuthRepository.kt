package com.android.cars.data.repository

import com.android.cars.data.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(code: String, redirectUri: String): Flow<Resource<Unit>>

    suspend fun logout(): Flow<Resource<Unit>>
}