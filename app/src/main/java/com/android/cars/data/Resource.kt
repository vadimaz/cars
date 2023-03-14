package com.android.cars.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Resource<out R> {
    /**
     * Use [Initial] status for single events (e.g.: login POST request):
     * create a function at a specific ViewModel class to reset [Success] or [Error]
     * (after processing result) status back to [Initial] status
     */
    object Initial : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>() {
        val message: String
            get() = exception.message ?: exception.toString()
    }

    override fun toString(): String {
        return when (this) {
            Initial -> "Initial"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }
}

val Resource<*>.initial
    get() = this is Resource.Initial

val Resource<*>.succeeded
    get() = this is Resource.Success && data != null

val Resource<*>.failed
    get() = this is Resource.Error

val Resource<*>.loading
    get() = this is Resource.Loading