package com.android.cars.ui.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.cars.R
import com.android.cars.data.models.photo.Photo
import com.android.cars.ui.components.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun PhotosScreen(
    photosViewModel: PhotosViewModel,
    openDrawer: () -> Unit,
    navigateToPhotoDetails: (id: String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.photos_screen_name),
                topAppBarIcon = TopAppBarIcon.MenuIcon,
                onIconClicked = { coroutineScope.launch { openDrawer() } }
            )
        }
    ) { innerPadding ->
        val photosResponse = photosViewModel.photosResponse.collectAsLazyPagingItems()

        PhotoScreenContent(
            photosResponse,
            navigateToPhotoDetails = navigateToPhotoDetails,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun PhotoScreenContent(
    photosResponse: LazyPagingItems<Photo>,
    navigateToPhotoDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val loadStatus = photosResponse.loadState.refresh

    SwipeRefresh(
        state = rememberSwipeRefreshState(loadStatus is LoadState.Loading),
        onRefresh = { photosResponse.refresh() },
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PhotoList(photosResponse, navigateToPhotoDetails)

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
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoList(
    photosResponse: LazyPagingItems<Photo>,
    navigateToPhotoDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cellsCount = 2
    LazyVerticalGrid( // TODO: need to wait for staggered grid layout
        cells = GridCells.Fixed(cellsCount),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp)
    ) {
        pagingLoadStateItem(
            loadState = photosResponse.loadState.prepend,
            cellsCount = cellsCount,
            onRefresh = { photosResponse.retry() }
        )

        items(photosResponse) { photo ->
            photo?.let {
                PhotoListItem(
                    photo = photo,
                    navigateToPhotoDetails = navigateToPhotoDetails
                )
            }
        }

        pagingLoadStateItem(
            loadState = photosResponse.loadState.append,
            cellsCount = cellsCount,
            onRefresh = { photosResponse.retry() }
        )
    }
}