package com.android.cars.data.remote.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.android.cars.R
import com.android.cars.data.remote.models.NetworkError
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

class NetworkErrorConverterHelper(
    private val context: Context,
    private val gson: Gson
) {
    fun parseError(throwable: Throwable): Throwable {
        return if (throwable is HttpException) {
            val errorType = object : TypeToken<NetworkError>() {}.type
            try {
                val parsedError: NetworkError =
                    gson.fromJson(throwable.response()?.errorBody()?.charStream(), errorType)
                Exception(parsedError.errors.joinToString("\n"))
            } catch (e: Exception) {
                throwable
            }
        } else if (!isNetworkConnected()) {
            Exception(context.getString(R.string.error_internet_is_not_available_text))
        } else {
            throwable
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            return cm.activeNetworkInfo?.isConnected == true
        }
    }
}