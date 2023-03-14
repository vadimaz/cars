package com.android.cars.data.models.photo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoverPhoto(
    val photoUrls: PhotoUrls?
) : Parcelable