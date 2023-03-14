package com.android.cars.data.remote.manager

import com.android.cars.data.models.auth.LoginResponse

interface AuthDataSource {

    suspend fun login(code: String, redirectUri: String): LoginResponse

    // TODO: it seems Unsplash doesn't support refresh token logic yet, access token lifetime is 1 year
    suspend fun refreshAccessToken(refreshToken: String)
}