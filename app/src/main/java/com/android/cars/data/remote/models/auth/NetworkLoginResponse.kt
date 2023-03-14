package com.android.cars.data.remote.models.auth

import com.android.cars.data.models.auth.LoginResponse
import com.google.gson.annotations.SerializedName

data class NetworkLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
) {
    companion object {
        fun mapToModel(network: NetworkLoginResponse) = LoginResponse(
            accessToken = network.accessToken,
            refreshToken = network.refreshToken
        )
    }
}