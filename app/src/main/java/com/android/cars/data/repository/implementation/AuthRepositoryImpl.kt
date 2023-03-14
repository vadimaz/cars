package com.android.cars.data.repository.implementation

import com.android.cars.data.Resource
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.remote.manager.AuthDataSource
import com.android.cars.data.repository.AuthRepository
import com.android.cars.util.wrapAsResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val preferencesManager: PreferencesManager
) : AuthRepository {

    override fun login(code: String, redirectUri: String): Flow<Resource<Unit>> = flow {
        val accessToken = authDataSource.login(code, redirectUri)
        preferencesManager.setToken(
            accessToken = accessToken.accessToken,
            refreshToken = accessToken.refreshToken
        )
        emit(Unit)
    }.wrapAsResult()

    override suspend fun logout(): Flow<Resource<Unit>> = flow {
        // TODO: request to logout
        preferencesManager.setToken(null, null)
        emit(Unit)
    }.wrapAsResult()
}