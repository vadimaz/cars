package com.android.cars.data.models.topic

import androidx.annotation.Keep

@Keep
enum class TopicsOrderByEnum(val type: String) {
    FEATURED("featured"),
    LATEST("latest"),
    OLDEST("oldest"),
    POSITION("position")
}