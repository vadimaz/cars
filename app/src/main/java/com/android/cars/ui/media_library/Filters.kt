package com.android.cars.ui.media_library

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.cars.R
import com.android.cars.ui.theme.AppTheme
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MediaLibraryFilters(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    mediaTypeFilters: List<String>,
    eventTypeFilters: List<String>,
    expiringSoonFilters: List<String>,
    onDone: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(expanded)
    }
    Column(modifier = modifier.fillMaxWidth().background(color = Color(0xFFEFF1F5))) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable { isExpanded = !isExpanded }
                .background(
                    color = if (isExpanded) {
                        Color(0xFF4285F4)
                    } else {
                        Color(0xFF6B7A99)
                    }
                )
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = stringResource(id = R.string.filter),
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = R.string.filter),
                style = MaterialTheme.typography.body1,
                color = Color.White
            )
            if (isExpanded) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        if (isExpanded) {
            Spacer(modifier = Modifier.height(20.dp))
            MediaLibraryFilterItemGroup(
                title = "Media Type",
                filters = listOf("Videos", "Images"),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            MediaLibraryFilterItemGroup(
                title = "Event Type",
                filters = listOf("OK Presto", "Automatic", "Requested"),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            MediaLibraryFilterItemGroup(
                title = "Expiring",
                filters = listOf("Expiring Soon"),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(Modifier.fillMaxWidth()) {
                Box(modifier = Modifier
                    .weight(.5f)
                    .height(66.dp)
                    .background(color = Color.White)
                    .clickable {
                        // TODO clear filters
                    }) {
                    Text(
                        text = "Clear All",
                        style = MaterialTheme.typography.body2,
                        color = Color(0xFF4285F4),
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                Box(modifier = Modifier
                    .weight(.5f)
                    .height(66.dp)
                    .background(color = Color(0xFF4285F4))
                    .clickable {
                        // TODO apply filters
                    }) {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun MediaLibraryFilterItem(
    modifier: Modifier = Modifier,
    title: String,
    checked: Boolean = false
) {
    var itemChecked by remember {
        mutableStateOf(checked)
    }
    Box(
        modifier = modifier
            .height(48.dp)
            .clickable { itemChecked = !itemChecked }
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = if (itemChecked) {
                    Color(0xFF4285F4)
                } else {
                    Color.White
                }
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(5.dp),
                color = if (itemChecked) {
                    Color(0xFF4285F4)
                } else {
                    Color(0xFFB6C6E5)
                }
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2,
            color = if (itemChecked) {
                Color.White
            } else {
                Color.Black
            },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun MediaLibraryFilterItemGroup(
    modifier: Modifier = Modifier,
    title: String,
    filters: List<String>
) {
    Column(modifier = modifier) {
        Text(
            text = title.uppercase(),
            fontSize = 12.sp,
            color = Color(0xFF6B7A99)
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            mainAxisSpacing = 10.dp
        ) {
            filters.forEach {
                MediaLibraryFilterItem(title = it)
            }
        }
    }
}

@Preview
@Composable
fun MediaLibraryFiltersDisabledPreview() {
    AppTheme {
        Surface {
            MediaLibraryFilters(
                expanded = false,
                mediaTypeFilters = emptyList(),
                eventTypeFilters = emptyList(),
                expiringSoonFilters = emptyList(),
                onDone = {}
            )
        }
    }
}

@Preview
@Composable
fun MediaLibraryFiltersEnabledPreview() {
    AppTheme {
        Surface {
            MediaLibraryFilters(
                expanded = true,
                mediaTypeFilters = emptyList(),
                eventTypeFilters = emptyList(),
                expiringSoonFilters = emptyList(),
                onDone = {}
            )
        }
    }
}

@Preview
@Composable
fun MediaLibraryFilterItemEnabledPreview() {
    AppTheme {
        MediaLibraryFilterItem(title = "Videos", checked = true)
    }
}

@Preview
@Composable
fun MediaLibraryFilterItemDisabledPreview() {
    AppTheme {
        MediaLibraryFilterItem(title = "Images", checked = false)
    }
}

@Preview
@Composable
fun MediaLibraryFilterItemGroupPreview() {
    AppTheme {
        Surface {
            MediaLibraryFilterItemGroup(
                title = "Media Type",
                filters = listOf("Videos", "Images")
            )
        }
    }
}