package com.android.cars.data.remote.converters

import com.android.cars.data.models.topic.TopicsOrderByEnum
import retrofit2.Converter
import javax.inject.Inject

class TopicOrderByConverter @Inject constructor() : Converter<TopicsOrderByEnum, String> {
    override fun convert(transactionStatus: TopicsOrderByEnum): String {
        return transactionStatus.type
    }
}
