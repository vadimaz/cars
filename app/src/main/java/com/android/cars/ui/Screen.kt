package com.android.cars.ui

enum class Screen {
    Map,
    MediaLibrary,
    Topics,
    Photos,
    TopicDetails,
    Login;

    companion object {
        // we use id to get object from local storage (ROOM) - single source of truth
        // instead of passing parceable object which is not recommended
        // and it is not supported by Navigation Compose without creating custom NavType
        const val ARG_TOPIC_ID = "topic_id"

        const val ARG_CODE = "code"

        const val UNSPLASH_AUTH_URI = "unsplash://o_auth"

        fun fromRoute(route: String?): Screen {
            return route
                ?.substringBefore("/")
                ?.substringBefore("?")
                ?.let { valueOf(it) } ?: Topics // Default screen
        }
    }
}
