package com.android.cars.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.android.cars.R

@Composable
fun SnackBar(
    text: String,
    modifier: Modifier = Modifier,
    buttonText: String? = null,
    onBtnClick: (() -> Unit)? = null
) {
    Snackbar(
        action = {
            onBtnClick?.let {
                Button(onClick = onBtnClick) {
                    Text(buttonText ?: stringResource(id = R.string.retry_text))
                }
            }
        },
        modifier = modifier.padding(8.dp)
    ) { Text(text = text) }
}



fun LazyListScope.pagingLoadStateItem(
    loadState: LoadState,
    onRefresh: () -> Unit
) {
    if (loadState == LoadState.Loading) {
        item {
            PagingLoadState()
        }
    } else if (loadState is LoadState.Error) {
        item {
            PagingErrorState(
                loadState = loadState,
                onRefresh = onRefresh
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyGridScope.pagingLoadStateItem(
    loadState: LoadState,
    cellsCount: Int,
    onRefresh: () -> Unit
) {
    if (loadState == LoadState.Loading) {
        item(span = { GridItemSpan(cellsCount) }) {
            PagingLoadState()
        }
    } else if (loadState is LoadState.Error) {
        item(span = { GridItemSpan(cellsCount) }) {
            PagingErrorState(
                loadState = loadState,
                onRefresh = onRefresh
            )
        }
    }
}

@Composable
fun PagingLoadState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun PagingErrorState(
    loadState: LoadState.Error,
    onRefresh: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = loadState.error.message ?: stringResource(id = R.string.unknown_error),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
        Button(onClick = onRefresh, modifier = Modifier.padding(top = 8.dp)) {
            Text(text = stringResource(id = R.string.retry_text))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}