package com.praisetechzw.phoneguard.feature.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun register(name: String, email: String, password: String, onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _error.value = "Please fill in all fields"
            return
        }

        if (password.length < 6) {
            _error.value = "Password must be at least 6 characters"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            // Mock API call
            delay(2000)
            
            userRepository.saveUserSession("mock_new_id", "mock_new_token")
            onSuccess()
            
            _isLoading.value = false
        }
    }
}
