package com.android.cars.data.models.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String // TODO: no documentation about refresh endpoint
)