package com.android.cars.data.remote.api

import com.android.cars.data.remote.models.auth.NetworkLoginResponse
import com.android.cars.data.remote.requests.NetworkLoginRequest
import com.android.cars.data.remote.requests.NetworkRefreshTokenRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("https://unsplash.com/oauth/token")
    suspend fun login(@Body networkLoginRequest: NetworkLoginRequest): NetworkLoginResponse

    @POST("https://unsplash.com/oauth/token")
    suspend fun refreshAccessToken(@Body networkRefreshTokenRequest: NetworkRefreshTokenRequest): Unit
}