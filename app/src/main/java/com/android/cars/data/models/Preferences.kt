package com.android.cars.data.models

import com.android.cars.data.models.topic.TopicsOrderByEnum
import kotlinx.serialization.Serializable

@Serializable
data class Preferences(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val topicsOrderBy: TopicsOrderByEnum = TopicsOrderByEnum.POSITION
) 