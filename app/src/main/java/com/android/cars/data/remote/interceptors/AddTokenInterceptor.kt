package com.android.cars.data.remote.interceptors

import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.cars.data.remote.utils.ACTION_NETWORK_UNAUTHORIZED
import com.android.cars.data.remote.utils.HEADER_AUTHORIZATION
import com.android.cars.data.remote.utils.HEADER_NO_AUTHENTICATION
import com.android.cars.data.local.manager.PreferencesManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class AddTokenInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferencesManager: PreferencesManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val noAuth = request.header(HEADER_NO_AUTHENTICATION)?.toBoolean() ?: false
        if (!noAuth) {
            val token = runBlocking { preferencesManager.getPreferences().accessToken }
            token?.let {
                request = request.newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, "Bearer $it")
                    .build()
            }
        }
        return chain.proceed(request).apply {
            if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                LocalBroadcastManager.getInstance(context)
                    .sendBroadcast(Intent(ACTION_NETWORK_UNAUTHORIZED))
            }
        }
    }
}