package com.android.cars.ui.components


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.primary
            )
    ) {
        Spacer(Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier.padding(start = 22.dp)
        )
        Spacer(Modifier.height(30.dp))
        NavigationItem.values().forEach { item ->
            DrawerButton(
                icon = item.icon,
                label = stringResource(id = item.titleRes),
                isSelected = currentScreen == item.screen,
                action = {
                    onTopLevelScreenNavigate(item.screen)
                    closeDrawer()
                },
                modifier = Modifier.padding(start = 30.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        VersionText(
            versionName = "3.0.5",
            versionCode = "59",
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 30.dp, bottom = 8.dp)
        )
        Divider(
            color = MaterialTheme.colors.onPrimary.copy(alpha = .9f),
            modifier = Modifier.padding(horizontal = 30.dp)
        )
        TextButton(
            onClick = {
                if (isAuthorized) {
                    onLogout()
                } else {
                    onLogin()
                }
                closeDrawer()
            },
            modifier = Modifier.padding(start = 41.dp)
        ) {
            Text(
                text = if (isAuthorized) stringResource(id = R.string.logout) else stringResource(id = R.string.login),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f)
            )
        }
        Spacer(modifier = Modifier.height(51.dp))
    }
}

@Composable
fun VersionText(
    versionName: String,
    versionCode: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$versionName ($versionCode)",
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.6f),
        modifier = modifier
    )
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
        colors.onPrimary
    } else {
        colors.onPrimary.copy(alpha = 0.6f)
    }

    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
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
                alpha = imageAlpha,
                modifier = Modifier.size(26.dp)
            )
            Spacer(Modifier.width(22.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                color = textIconColor
            )
        }
    }
}

@Preview("Drawer contents")
@Composable
fun PreviewAppDrawer() {
    AppTheme {
        Surface {
            AppDrawer(
                currentScreen = Screen.MediaLibrary,
                onTopLevelScreenNavigate = {},
                isAuthorized = true,
                onLogin = {},
                onLogout = {},
                closeDrawer = { }
            )
        }
    }
}
