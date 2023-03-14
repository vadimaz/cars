package com.android.cars.ui.components

import android.content.res.Configuration
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.cars.ui.NavigationItem
import com.android.cars.ui.Screen
import com.android.cars.ui.theme.AppTheme

@Composable
fun AppBottomBar(
    currentScreen: Screen,
    onTopLevelScreenNavigate: (Screen) -> Unit
) {
    BottomNavigation {
        NavigationItem.values().forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = { Text(stringResource(item.titleRes)) },
                selected = (currentScreen == item.screen),
                onClick = { onTopLevelScreenNavigate(item.screen) }
            )
        }
    }
}


@Preview("Regular colors")
@Preview("Dark colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewBottomBar() {
    AppTheme {
        Scaffold(
            bottomBar = {
                AppBottomBar(
                    currentScreen = Screen.Topics,
                    onTopLevelScreenNavigate = {}
                )
            }
        ) {}
    }
}