package com.android.cars.data.remote.interceptors

import com.android.cars.data.remote.utils.HEADER_AUTHORIZATION
import com.android.cars.data.remote.utils.UNSPLASH_API_ACCESS_KEY
import com.android.cars.data.remote.utils.UNSPLASH_API_VERSION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val newRequest = request().newBuilder()
            .addHeader("accept", "application/json")
            // Unsplash Developer Access Key https://unsplash.com/documentation#public-authentication
            .addHeader(HEADER_AUTHORIZATION, "Client-ID $UNSPLASH_API_ACCESS_KEY")
            .addHeader("Accept-Version", UNSPLASH_API_VERSION)
            //.addHeader("Accept-Language", /* GET_USER_PREF_LANG */)
            .build()
        proceed(newRequest)
    }
}