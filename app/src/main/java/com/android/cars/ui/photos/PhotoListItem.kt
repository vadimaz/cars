package com.android.cars.ui.photos

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.photo.PhotoUrls
import com.android.cars.ui.theme.AppTheme
import com.android.cars.ui.theme.PhotoDescBackground
import com.android.cars.util.Formats
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


@Composable
fun PhotoListItem(
    photo: Photo,
    modifier: Modifier = Modifier,
    navigateToPhotoDetails: (id: String) -> Unit = {},
) {
    val ratio = if (photo.width != null && photo.height != null) {
        photo.width.toFloat() / photo.height.toFloat()
    } else 0f
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(ratio)
            .clickable { navigateToPhotoDetails(photo.id) }
    ) {
        Box {
            val imageUrl = photo.photoUrls.let {
                it.thumb ?: it.small ?: it.raw ?: it.regular ?: it.full
            }
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = "Photo image",
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        photo.color?.let { Color(android.graphics.Color.parseColor(it)) }
                            ?: Color.DarkGray
                    )
            )
            photo.description?.let {
                Text(
                    text = Formats.removeHtmlTags(photo.description),
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(6.dp)
                        .background(color = PhotoDescBackground, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}


@Preview("Regular colors")
@Preview("Dark colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewPhotoItem() {
    AppTheme {
        Surface {
            PhotoListItem(
                Photo(
                    id = UUID.randomUUID().toString(),
                    description = "Photo description",
                    createdAt = LocalDateTime.now(ZoneOffset.UTC),
                    color = null,
                    width = 300,
                    height = 200,
                    photoUrls = PhotoUrls(null, null, null, null, null)
                )
            ) {}
        }
    }
}