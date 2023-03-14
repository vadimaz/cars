package com.android.cars.data.remote.requests

import com.android.cars.data.remote.utils.OauthGrantType
import com.google.gson.annotations.SerializedName

data class NetworkRefreshTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken: String,

    @SerializedName("grant_type")
    val grantType: OauthGrantType
)
