package com.android.cars.data.models.photo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUrls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
) : Parcelable