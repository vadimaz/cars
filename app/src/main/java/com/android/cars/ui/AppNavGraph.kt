package com.android.cars.ui

import androidx.annotation.StringRes
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.cars.R
import com.android.cars.ui.login.LoginScreen
import com.android.cars.ui.media_library.MediaLibraryScreen
import com.android.cars.ui.photos.PhotosScreen
import com.android.cars.ui.topic_details.TopicDetailsScreen
import com.android.cars.ui.topics.TopicsScreen
import kotlinx.coroutines.launch


@Composable
fun AppNavGraph(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.MediaLibrary.name,
) {
    val actions = remember(navController) { MainActions(navController) }
    val coroutineScope = rememberCoroutineScope()
    val openDrawer: () -> Unit = { coroutineScope.launch { scaffoldState.drawerState.open() } }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.MediaLibrary.name) {
            MediaLibraryScreen(
                openDrawer = openDrawer,
                navigateToTopicDetails = {  }
            )
        }
        composable(route = Screen.Topics.name) {
            TopicsScreen(
                topicsViewModel = hiltViewModel(),
                openDrawer = openDrawer,
                navigateToTopicDetails = { id -> actions.navigateToTopicsDetails(id) }
            )
        }
        composable(route = Screen.Photos.name) {
            PhotosScreen(
                photosViewModel = hiltViewModel(),
                openDrawer = openDrawer,
                navigateToPhotoDetails = {}
            )
        }
        composable(
            route = "${Screen.TopicDetails}/{${Screen.ARG_TOPIC_ID}}",
            arguments = listOf(navArgument(Screen.ARG_TOPIC_ID) { type = NavType.StringType })
        ) { entry ->
            val topicId = requireNotNull(entry.arguments?.getString(Screen.ARG_TOPIC_ID))
            TopicDetailsScreen(
                topicDetailsViewModel = hiltViewModel(),
                topicId = topicId,
                navigateUp = actions.upPress,
                navigateToPhotoDetails = {}
            )
        }
        composable(
            route = "${Screen.Login.name}?${Screen.ARG_CODE}={${Screen.ARG_CODE}}",
            deepLinks = listOf(navDeepLink {
                uriPattern = "${Screen.UNSPLASH_AUTH_URI}?${Screen.ARG_CODE}={${Screen.ARG_CODE}}"
            })
        ) { entry ->
            val code = requireNotNull(entry.arguments?.getString(Screen.ARG_CODE))
            LoginScreen(
                loginViewModel = hiltViewModel(),
                unsplashAuthCode = code,
                navigateUp = actions.upPress
            )
        }
    }
}

/**
 * Models the navigation actions in the app.
 */
private class MainActions(navController: NavHostController) {
    val navigateToTopicsDetails: (id: String) -> Unit = { topicId: String ->
        navController.navigate("${Screen.TopicDetails}/$topicId")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}

enum class NavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    @StringRes val titleRes: Int
) {
    Map(Screen.Map, Icons.Filled.Map, R.string.map_name),
    MediaLibrary(Screen.MediaLibrary, Icons.Filled.LinkedCamera, R.string.media_library_name)
}

fun navigateTopLevelScreen(navController: NavController, screen: Screen) {
    navController.navigate(screen.name) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}