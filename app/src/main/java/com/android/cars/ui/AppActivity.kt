package com.android.cars.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.cars.R
import com.android.cars.data.remote.utils.UNSPLASH_API_ACCESS_KEY
import com.android.cars.ui.components.AppBottomBar
import com.android.cars.ui.components.AppDrawer
import com.android.cars.ui.theme.AppTheme
import com.android.cars.util.IntentUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchApp()
        }
    }
}

@Composable
fun ArchApp() {
    val topLevelDestinations = setOf(Screen.Topics, Screen.Photos)

    val appViewModel: MainViewModel = hiltViewModel()

    AppTheme {
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = Screen.fromRoute(backstackEntry.value?.destination?.route)

        val isTopLevelDestination by derivedStateOf { currentScreen in topLevelDestinations }

        // This top level scaffold contains the app drawer, which needs to be accessible
        // from multiple screens. An event to open the drawer is passed down to each
        // screen that needs it.
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = if (isTopLevelDestination) {
                {
                    val isAuthorized by appViewModel.isAuthorized.collectAsState()
                    val context = LocalContext.current
                    AppDrawer(
                        currentScreen = currentScreen,
                        onTopLevelScreenNavigate = { screen ->
                            navigateTopLevelScreen(navController, screen)
                        },
                        isAuthorized = isAuthorized,
                        onLogin = {
                            IntentUtils.openLink(
                                context,
                                context.getString(
                                    R.string.unsplash_authorize_link,
                                    UNSPLASH_API_ACCESS_KEY,
                                    context.getString(R.string.aouth_login_deep_link)
                                )
                            )
                        },
                        onLogout = {
                            appViewModel.logout()
                            Toast.makeText(
                                context,
                                R.string.logout_success_message,
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        closeDrawer = {
                            scope.launch { scaffoldState.drawerState.close() }
                        }
                    )
                }
            } else {
                null
            },
            bottomBar = {
                if (isTopLevelDestination) {
                    AppBottomBar(
                        currentScreen = currentScreen,
                        onTopLevelScreenNavigate = { screen ->
                            navigateTopLevelScreen(navController, screen)
                        }
                    )
                }
            }
        ) { innerPadding ->
            AppNavGraph(navController, scaffoldState, modifier = Modifier.padding(innerPadding))
        }
    }
}