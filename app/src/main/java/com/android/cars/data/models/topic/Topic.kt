package com.android.cars.data.models.topic

import android.os.Parcelable
import androidx.annotation.Keep
import com.android.cars.data.models.photo.CoverPhoto
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

// keep models which you pass as argument using Navigation Component
// https://developer.android.com/guide/navigation/navigation-pass-data#use_keep_annotations
@Keep
@Parcelize
data class Topic(
    val id: String,
    val title: String,
    val description: String?,
    val publishedAt: LocalDateTime,
    val coverPhoto: CoverPhoto?
) : Parcelable