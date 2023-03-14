package com.android.cars.ui.topic_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.android.cars.R
import com.android.cars.data.models.photo.Photo
import com.android.cars.data.models.topic.Topic
import com.android.cars.ui.components.AppTopAppBar
import com.android.cars.ui.components.SnackBar
import com.android.cars.ui.components.TopAppBarIcon
import com.android.cars.ui.components.pagingLoadStateItem
import com.android.cars.ui.photos.PhotoListItem
import com.android.cars.util.Formats

@Composable
fun TopicDetailsScreen(
    topicDetailsViewModel: TopicDetailsViewModel,
    topicId: String,
    navigateUp: () -> Unit,
    navigateToPhotoDetails: (id: String) -> Unit
) {
    LaunchedEffect(Unit) {
        topicDetailsViewModel.getTopicById(topicId)
        topicDetailsViewModel.loadPhotos(topicId)
    }

    val topic by topicDetailsViewModel.topic.collectAsState()
    val photosResponse = topicDetailsViewModel.photosResponse.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = topic?.title,
                topAppBarIcon = TopAppBarIcon.BackIcon,
                onIconClicked = navigateUp
            )
        }
    ) { innerPadding ->
        TopicDetailsContent(
            modifier = Modifier.padding(innerPadding),
            topic = topic,
            photosResponse = photosResponse,
            navigateToPhotoDetails = navigateToPhotoDetails
        )
    }
}

@Composable
fun TopicDetailsContent(
    modifier: Modifier,
    topic: Topic?,
    photosResponse: LazyPagingItems<Photo>,
    navigateToPhotoDetails: (id: String) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        topic?.let { topic ->
            item {
                Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                    Text(
                        text = topic.title,
                        style = MaterialTheme.typography.h2
                    )
                    topic.description?.let { description ->
                        Text(
                            text = Formats.removeHtmlTags(description),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
        item {
            TopicPhotos(
                photosResponse,
                navigateToPhotoDetails = navigateToPhotoDetails
            )
        }
    }
}

@Composable
fun TopicPhotos(
    photosResponse: LazyPagingItems<Photo>,
    navigateToPhotoDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        TopicDetailsPhotoList(photosResponse, navigateToPhotoDetails)

        val loadStatus = photosResponse.loadState.refresh
        if (loadStatus is LoadState.NotLoading && photosResponse.itemSnapshotList.isEmpty()) {
            Text(
                text = stringResource(id = R.string.photos_empty_placeholder_text),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp)
            )
        } else if (loadStatus is LoadState.Error) {
            SnackBar(
                text = loadStatus.error.message ?: stringResource(id = R.string.unknown_error),
                onBtnClick = { photosResponse.refresh() },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        } else if (loadStatus is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            )
        }
    }
}

@Composable
fun TopicDetailsPhotoList(
    photosResponse: LazyPagingItems<Photo>,
    navigateToPhotoDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        pagingLoadStateItem(
            loadState = photosResponse.loadState.prepend,
            onRefresh = { photosResponse.retry() }
        )

        items(photosResponse) { photo ->
            photo?.let {
                PhotoListItem(
                    photo = photo,
                    modifier = Modifier.height(300.dp),
                    navigateToPhotoDetails = navigateToPhotoDetails
                )
            }
        }

        pagingLoadStateItem(
            loadState = photosResponse.loadState.append,
            onRefresh = { photosResponse.retry() }
        )
    }
}