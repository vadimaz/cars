package com.android.cars.data.remote.api

import com.android.cars.data.remote.models.photo.NetworkPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<NetworkPhoto>
}