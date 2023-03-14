package com.android.cars.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cars.data.Resource
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    preferencesManager: PreferencesManager
) : ViewModel() {

    private var jobLogout: Job? = null
    private val _logoutResponse = MutableStateFlow<Resource<Unit>>(Resource.Initial)
    val logoutResponse = _logoutResponse.asStateFlow()

    val isAuthorized = preferencesManager.isAuthorized()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun logout() {
        jobLogout?.cancel()
        jobLogout = viewModelScope.launch {
            authRepository.logout().collect {
                _logoutResponse.emit(it)
            }
        }
    }

    fun resetLogoutResponse() {
        _logoutResponse.value = Resource.Initial
    }
}