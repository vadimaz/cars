package com.android.cars.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.cars.R
import com.android.cars.ui.theme.AppTheme
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

sealed class TopAppBarIcon {
    object NoneIcon : TopAppBarIcon()
    object BackIcon : TopAppBarIcon()
    object MenuIcon : TopAppBarIcon()
    abstract class CustomIcon : TopAppBarIcon() {
        @Composable
        abstract fun Icon()
    }
}


/**
 * A wrapper around [AppTopAppBar] which uses [Modifier.statusBarsPadding] to shift the app bar's
 * contents down, but still draws the background behind the status bar too.
 */
@Composable
fun AppTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    topAppBarIcon: TopAppBarIcon = TopAppBarIcon.NoneIcon,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp,
    onIconClicked: () -> Unit = {}
) {
    Surface(
        color = backgroundColor,
        elevation = elevation,
        modifier = modifier
    ) {
        TopAppBar(
            title = title?.let { { Text(text = title) } } ?: {},
            navigationIcon = { ToolbarIcon(topAppBarIcon, onIconClicked) },
            actions = actions,
            backgroundColor = Color.Transparent,
            contentColor = contentColor,
            elevation = 0.dp,
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding(bottom = false)
        )
    }
}

@Composable
fun ToolbarIcon(
    topAppBarIcon: TopAppBarIcon = TopAppBarIcon.NoneIcon,
    onIconClicked: () -> Unit
) {
    when (topAppBarIcon) {
        TopAppBarIcon.NoneIcon -> {
            // without icon
        }
        TopAppBarIcon.BackIcon -> {
            IconButton(onClick = { onIconClicked() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.content_description_drawer_button)
                )
            }
        }
        TopAppBarIcon.MenuIcon -> {
            IconButton(onClick = { onIconClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.content_description_navigate_up)
                )
            }
        }
        is TopAppBarIcon.CustomIcon -> {
            IconButton(onClick = { onIconClicked() }) {
                topAppBarIcon.Icon()
            }
        }
    }
}

@Preview("Regular colors")
@Preview("Dark colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewToolbar() {
    AppTheme {
        Scaffold(
            topBar = {
                AppTopAppBar(
                    title = stringResource(id = R.string.topics_screen_name),
                    topAppBarIcon = TopAppBarIcon.MenuIcon
                )
            }
        ) {}
    }
}