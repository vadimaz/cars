package com.android.cars.ui.media_library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.cars.R
import com.android.cars.data.local.models.media.MediaContent
import com.android.cars.data.local.models.media.MediaContentStatus
import com.android.cars.data.local.models.media.MediaContentType
import com.android.cars.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun MediaLibraryScreen(
    openDrawer: () -> Unit,
    navigateToTopicDetails: (id: String) -> Unit,
) {
    Surface {
        MediaLibrary(list = mediaContentList)
    }
}

@Composable
fun MediaLibraryItem(item: MediaContent, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            AsyncImage(
                model = item.url,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.test_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(150f / 110f)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.primary.copy(alpha = .5f)
                    )
            )
            if (item.status == MediaContentStatus.EXPIRING_SOON) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = .6f))
                        .padding(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.expiring_soon),
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            Icon(
                imageVector = if (item.type == MediaContentType.VIDEO) {
                    Icons.Filled.PlayCircle
                } else {
                    Icons.Filled.PhotoCamera
                },
                contentDescription = "Play",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = item.publishedAt.format(
                DateTimeFormatter.ofPattern("hh:mm:ss a")
            ),
            style = MaterialTheme.typography.body2
                .copy(fontWeight = FontWeight.SemiBold),
        )
        item.duration?.let {
            Text(
                text = "${it}s",
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaLibrary(
    modifier: Modifier = Modifier,
    list: List<MediaContent> = emptyList()
) {
    val cellCount = 2
    Column(modifier = modifier.fillMaxSize()) {
        MediaLibraryFilter()
        LazyVerticalGrid(
            cells = GridCells.Fixed(cellCount),
            state = rememberLazyListState()
        ) {
            list.groupBy { it.publishedAt.toLocalDate() }.forEach { (date, contentList) ->
                item(span = { GridItemSpan(cellCount) }) {
                    MediaLibraryListHeader(date = date)
                }
                items(contentList) { content ->
                    MediaLibraryItem(item = content)
                }
            }


        }
    }
}

@Composable
fun MediaLibraryListHeader(date: LocalDate, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color(0xFFDEE3EA))
    ) {
        Text(
            text = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)),
            style = MaterialTheme.typography.body1,
            color = Color(0xFF6B7A99),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun MediaLibraryFilter(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color(0xFF6B7A99))
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = stringResource(id = R.string.filter),
            tint = Color.White,
            modifier = Modifier
                .width(20.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = R.string.filter),
            style = MaterialTheme.typography.body1,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun MediaLibraryPreview() {
    AppTheme {
        Surface {
            MediaLibrary(list = mediaContentList)
        }
    }
}

@Preview
@Composable
fun MediaLibraryItemPreview() {
    AppTheme {
        Surface {
            MediaLibraryItem(
                item = MediaContent(
                    id = UUID.randomUUID().toString(),
                    type = MediaContentType.PHOTO,
                    publishedAt = LocalDateTime.now(),
                    url = "https://i.ibb.co/31fvhZ1/image1.png",
                    status = MediaContentStatus.EXPIRING_SOON,
                    duration = 12L
                ),
                modifier = Modifier.width(180.dp)
            )
        }

    }
}

@Preview
@Composable
fun MediaLibraryListHeaderPreview() {
    AppTheme {
        Surface {
            MediaLibraryListHeader(date = LocalDateTime.now().toLocalDate())
        }
    }
}

@Preview
@Composable
fun MediaLibraryFilterPreview() {
    AppTheme {
        Surface {
            MediaLibraryFilter()
        }
    }
}

val mediaContentList = listOf(
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 14, 1, 56, 18),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.AVAILABLE,
        duration = 12L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 14, 5, 5, 10),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.AVAILABLE,
        duration = 40L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.PHOTO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 14, 10, 45, 1),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.EXPIRING_SOON,
        duration = 120L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 14, 13, 12, 12),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.EXPIRING_SOON,
        duration = 35L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 15, 14, 14, 14),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.AVAILABLE,
        duration = 12L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 15, 15, 15, 15),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.AVAILABLE,
        duration = 40L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.PHOTO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 15, 15, 16, 17),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.EXPIRING_SOON,
        duration = 120L
    ),
    MediaContent(
        id = UUID.randomUUID().toString(),
        type = MediaContentType.VIDEO,
        publishedAt = LocalDateTime.of(2023, Month.MAY, 15, 17, 17, 17),
        url = "https://i.ibb.co/31fvhZ1/image1.png",
        status = MediaContentStatus.EXPIRING_SOON,
        duration = 35L
    )
)