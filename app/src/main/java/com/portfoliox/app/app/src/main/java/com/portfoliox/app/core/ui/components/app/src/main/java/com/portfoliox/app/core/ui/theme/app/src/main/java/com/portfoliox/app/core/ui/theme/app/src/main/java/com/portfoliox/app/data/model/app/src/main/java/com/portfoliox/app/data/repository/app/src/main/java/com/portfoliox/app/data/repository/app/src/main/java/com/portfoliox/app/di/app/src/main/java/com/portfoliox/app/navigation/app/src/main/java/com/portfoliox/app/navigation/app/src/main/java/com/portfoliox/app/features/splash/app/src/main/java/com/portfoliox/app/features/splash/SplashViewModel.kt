package com.portfoliox.app.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.portfoliox.app.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashDestination {
    data object Loading : SplashDestination()
    data object Home : SplashDestination() // whether or not an admin is logged in, visitors land on Home
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Loading)
    val destination: StateFlow<SplashDestination> = _destination.asStateFlow()

    init {
        viewModelScope.launch {
            // Supabase Auth (installed with autoLoadFromStorage = true) restores
            // any saved session here automatically - this IS the "auto login".
            authRepository.isLoggedIn.collect {
                _destination.value = SplashDestination.Home
            }
        }
    }
}
