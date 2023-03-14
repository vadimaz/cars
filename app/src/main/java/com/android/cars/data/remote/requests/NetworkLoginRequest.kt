package com.android.cars.data.remote.requests

import com.android.cars.data.remote.utils.OauthGrantType
import com.google.gson.annotations.SerializedName

data class NetworkLoginRequest(
    @SerializedName("client_id")
    val clientId: String,

    @SerializedName("client_secret")
    val clientSecret: String,

    @SerializedName("redirect_uri")
    val redirectUri: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("grant_type")
    val grantType: OauthGrantType
)
