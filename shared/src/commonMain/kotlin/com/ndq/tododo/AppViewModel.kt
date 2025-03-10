package com.ndq.tododo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ndq.tododo.repositories.PreferencesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class AppViewModel(private val preferencesDao: PreferencesDao): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    val preferences = preferencesDao.getFlow()
        .onEach {
            if (isLoading.value) {
                _isLoading.value = false;
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )
}
