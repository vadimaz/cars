package com.android.cars.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LinkedCamera
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.cars.R
import com.android.cars.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    AppTheme {
        Scaffold(
            topBar = { CarAppTopBar(title = "Hello, world!") }
        ) {

        }
    }
}

@Composable
fun CarAppTopBar(
    modifier: Modifier = Modifier,
    title: String?
) {
    TopAppBar(
        title = {
            title?.let {
                Row {
                    Text(text = it)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        Modifier
                            .align(Alignment.Bottom)
                            .padding(horizontal = 8.dp)
                    )
                }
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null
            )
        },
        actions = {
            AppBarButton(
                title = stringResource(id = R.string.request),
                icon = Icons.Filled.LinkedCamera
            ) {
                // TODO click
            }
        },
        modifier = modifier,
        backgroundColor = Color(0xFF2B2E42),
        contentColor = Color.White
    )
}

@Preview
@Composable
fun CarAppTopBarPreview() {
    CarAppTopBar(title = "Kayla's car")
}

@Composable
fun AppBarButton(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = Color(0xFF4285F4),
                shape = CircleShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.W500
        )
    }
}

@Preview
@Composable
fun AppBarButtonPreview() {
    AppTheme {
        AppBarButton(
            title = "Request",
            icon = Icons.Default.LinkedCamera
        ) {}
    }
}


















