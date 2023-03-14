package com.android.cars.ui.topics

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.cars.R
import com.android.cars.data.Resource
import com.android.cars.data.loading
import com.android.cars.data.models.topic.Topic
import com.android.cars.data.models.topic.TopicsOrderByEnum
import com.android.cars.data.succeeded
import com.android.cars.ui.components.AppTopAppBar
import com.android.cars.ui.components.DialogSingleChoice
import com.android.cars.ui.components.SnackBar
import com.android.cars.ui.components.TopAppBarIcon
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun TopicsScreen(
    topicsViewModel: TopicsViewModel,
    openDrawer: () -> Unit,
    navigateToTopicDetails: (id: String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var showOrderTypeDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.topics_screen_name),
                topAppBarIcon = TopAppBarIcon.MenuIcon,
                onIconClicked = { coroutineScope.launch { openDrawer() } },
                actions = {
                    IconButton(onClick = { showOrderTypeDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Sort,
                            contentDescription = stringResource(id = R.string.content_description_order)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val topicsResponse: Resource<Unit> by topicsViewModel.topicsResponse.collectAsState()
        val topics: List<Topic> by topicsViewModel.topics.collectAsState()

        TopicScreenContent(
            topicsResponse,
            topics,
            onLoadTopics = { topicsViewModel.loadTopics() },
            navigateToTopicDetails = navigateToTopicDetails,
            modifier = Modifier.padding(innerPadding)
        )
    }

    if (showOrderTypeDialog) {
        val orders = TopicsOrderByEnum.values()
        val currentOrder by topicsViewModel.topicsOrder.collectAsState()

        DialogSingleChoice(
            title = stringResource(id = R.string.topics_order_by_text),
            options = orders.map { it.name },
            selectedIndex = orders.indexOf(currentOrder),
            onIndexSelected = { selectedIdx ->
                topicsViewModel.updateTopicSorting(orders[selectedIdx])
                showOrderTypeDialog = false
            },
            onDismiss = { showOrderTypeDialog = false }
        )
    }
}

@Composable
fun TopicScreenContent(
    topicsResponse: Resource<Unit>,
    topics: List<Topic>,
    onLoadTopics: () -> Unit,
    navigateToTopicDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(topicsResponse.loading),
        onRefresh = onLoadTopics,
        modifier = modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (topicsResponse.succeeded && topics.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.topics_empty_placeholder_text),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                )
            } else {
                TopicList(topics, navigateToTopicDetails)
            }

            val topicsResponseLocal: Resource<Unit> = topicsResponse
            if (topicsResponseLocal is Resource.Error) {
                SnackBar(
                    text = topicsResponseLocal.message,
                    onBtnClick = onLoadTopics,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun TopicList(
    topics: List<Topic>,
    navigateToTopicDetails: (id: String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(topics) { index, topic ->
            if (index != 0) {
                TopicDivider()
            }
            TopicListItem(topic, navigateToTopicDetails)
        }
    }
}

@Composable
private fun TopicDivider() {
    Divider(
        modifier = Modifier,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    )
}