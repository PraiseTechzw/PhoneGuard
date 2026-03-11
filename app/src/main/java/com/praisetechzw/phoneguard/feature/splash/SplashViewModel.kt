package com.praisetechzw.phoneguard.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.UserRepository
import com.praisetechzw.phoneguard.core.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        checkNavigation()
    }

    private fun checkNavigation() {
        viewModelScope.launch {
            val isOnboardingCompleted = userRepository.isOnboardingCompleted.first()
            val userToken = userRepository.userToken.first()

            _startDestination.value = when {
                !isOnboardingCompleted -> Screen.Onboarding.route
                userToken == null -> Screen.Login.route
                else -> Screen.Dashboard.route
            }
        }
    }
}
