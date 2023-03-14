package com.android.cars.ui.components


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.cars.R
import com.android.cars.ui.NavigationItem
import com.android.cars.ui.Screen
import com.android.cars.ui.theme.AppTheme

@Composable
fun AppDrawer(
    currentScreen: Screen,
    onTopLevelScreenNavigate: (Screen) -> Unit,
    isAuthorized: Boolean,
    onLogin: () -> Unit,
    onLogout: () -> Unit,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(24.dp))
        Image(
            imageVector = Icons.Filled.Android,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            modifier = Modifier
                .size(96.dp)
                .padding(16.dp)
        )
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        NavigationItem.values().forEach { item ->
            DrawerButton(
                icon = item.icon,
                label = stringResource(id = item.titleRes),
                isSelected = currentScreen == item.screen,
                action = {
                    onTopLevelScreenNavigate(item.screen)
                    closeDrawer()
                }
            )
        }
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))
        if (!isAuthorized) {
            DrawerButton(
                icon = Icons.Filled.Login,
                label = stringResource(id = R.string.login),
                isSelected = false,
                action = {
                    onLogin()
                    closeDrawer()
                }
            )
        } else {
            DrawerButton(
                icon = Icons.Filled.Logout,
                label = stringResource(id = R.string.logout),
                isSelected = false,
                action = {
                    onLogout()
                    closeDrawer()
                }
            )
        }
    }
}

@Composable
private fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(textIconColor),
                    alpha = imageAlpha
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                    color = textIconColor
                )
            }
        }
    }
}

@Preview("Drawer contents")
@Preview("Drawer contents (dark)", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewAppDrawer() {
    AppTheme {
        Surface {
            AppDrawer(
                currentScreen = Screen.Topics,
                onTopLevelScreenNavigate = {},
                isAuthorized = false,
                onLogin = {},
                onLogout = {},
                closeDrawer = { }
            )
        }
    }
}
