package com.android.cars.data.remote.models

import com.google.gson.annotations.SerializedName

data class NetworkError(
    @SerializedName("errors")
    val errors: List<String>
)
