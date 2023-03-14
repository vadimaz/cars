package com.android.cars.ui.topics

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.android.cars.data.models.topic.Topic
import com.android.cars.ui.theme.AppTheme
import com.android.cars.util.Formats
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


@Composable
fun TopicListItem(
    topic: Topic,
    navigateToTopicDetails: (id: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navigateToTopicDetails(topic.id) }
    ) {
        val imageUrl = topic.coverPhoto?.photoUrls?.let {
            it.thumb ?: it.small ?: it.raw ?: it.regular ?: it.full
        }
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "Topic Logo",
            modifier = Modifier
                .size(72.dp)
                .background(Color.Black)
        )
        Spacer(Modifier.width(8.dp))
        Column {
            Text(
                text = topic.title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            topic.description?.let {
                Text(
                    text = Formats.removeHtmlTags(topic.description),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
            Text(
                text = Formats.dateFormat(topic.publishedAt),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview("Regular colors")
@Preview("Dark colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewTopicItem() {
    AppTheme {
        Surface {
            TopicListItem(
                Topic(
                    id = UUID.randomUUID().toString(),
                    title = "Test topic",
                    description = "Some description",
                    publishedAt = LocalDateTime.now(ZoneOffset.UTC),
                    coverPhoto = null
                )
            ) {}
        }
    }
}