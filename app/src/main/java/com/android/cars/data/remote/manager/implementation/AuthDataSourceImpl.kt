package com.android.cars.data.remote.manager.implementation

import com.android.cars.data.models.auth.LoginResponse
import com.android.cars.data.remote.api.AuthApi
import com.android.cars.data.remote.manager.AuthDataSource
import com.android.cars.data.remote.models.auth.NetworkLoginResponse
import com.android.cars.data.remote.requests.NetworkLoginRequest
import com.android.cars.data.remote.requests.NetworkRefreshTokenRequest
import com.android.cars.data.remote.utils.*

class AuthDataSourceImpl(
    private val authApi: AuthApi,
    private val networkErrorConverterHelper: NetworkErrorConverterHelper
) : AuthDataSource {

    override suspend fun login(code: String, redirectUri: String): LoginResponse = try {
        val data = authApi.login(
            NetworkLoginRequest(
                clientId = UNSPLASH_API_ACCESS_KEY,
                clientSecret = UNSPLASH_API_SECRET_KEY,
                code = code,
                redirectUri = redirectUri,
                grantType = OauthGrantType.AUTHORIZATION_CODE
            )
        )
        NetworkLoginResponse.mapToModel(data)
    } catch (e: Throwable) {
        throw networkErrorConverterHelper.parseError(e)
    }

    override suspend fun refreshAccessToken(refreshToken: String) = try {
        authApi.refreshAccessToken(
            NetworkRefreshTokenRequest(
                refreshToken = refreshToken,
                grantType = OauthGrantType.REFRESH_TOKEN
            )
        )
    } catch (e: Throwable) {
        throw networkErrorConverterHelper.parseError(e)
    }
}