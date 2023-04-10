package com.android.cars.ui.media_library

import androidx.lifecycle.ViewModel
import com.android.cars.data.local.manager.PreferencesManager
import com.android.cars.data.repository.MediaLibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MediaLibraryViewModel @Inject constructor(
    //private val mediaLibraryRepository: MediaLibraryRepository,
    private val preferencesManager: PreferencesManager
): ViewModel() {
}