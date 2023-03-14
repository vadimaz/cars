package com.android.cars.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cars.data.Resource
import com.android.cars.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var jobLogin: Job? = null
    private val _loginResponse = MutableStateFlow<Resource<Unit>>(Resource.Initial)
    val loginResponse = _loginResponse.asStateFlow()

    fun login(code: String, redirectUri: String) {
        jobLogin?.cancel()
        jobLogin = viewModelScope.launch {
            authRepository.login(code, redirectUri).collect {
                _loginResponse.emit(it)
            }
        }
    }
}