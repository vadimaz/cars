package com.android.cars.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.android.cars.R
import com.android.cars.data.Resource
import com.android.cars.ui.Screen
import com.android.cars.ui.components.AppTopAppBar
import com.android.cars.ui.components.SnackBar
import com.android.cars.ui.components.TopAppBarIcon
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    unsplashAuthCode: String,
    navigateUp: () -> Unit
) {
    LaunchedEffect(Unit) {
        loginViewModel.login(unsplashAuthCode, Screen.UNSPLASH_AUTH_URI)
    }

    Scaffold(
        topBar = {
            AppTopAppBar(
                title = stringResource(id = R.string.login),
                topAppBarIcon = TopAppBarIcon.BackIcon,
                onIconClicked = navigateUp
            )
        }
    ) { innerPadding ->
        val loginResponse by loginViewModel.loginResponse.collectAsState()

        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val loginResponseLocal: Resource<Unit> = loginResponse) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    Text(
                        text = stringResource(R.string.authorized),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    LaunchedEffect(Unit) {
                        delay(1500L)
                        navigateUp()
                    }
                }
                is Resource.Error -> {
                    SnackBar(
                        text = loginResponseLocal.message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    LaunchedEffect(Unit) {
                        delay(2000L)
                        navigateUp()
                    }
                }
                Resource.Initial -> {
                    // ignored
                }
            }
        }
    }
}