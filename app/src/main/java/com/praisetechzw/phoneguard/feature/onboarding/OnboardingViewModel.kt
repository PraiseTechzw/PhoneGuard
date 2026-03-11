package com.praisetechzw.phoneguard.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun completeOnboarding(onFinished: () -> Unit) {
        viewModelScope.launch {
            userRepository.setOnboardingCompleted(true)
            onFinished()
        }
    }
}
