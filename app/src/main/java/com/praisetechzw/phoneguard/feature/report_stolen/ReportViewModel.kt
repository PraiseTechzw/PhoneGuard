package com.praisetechzw.phoneguard.feature.report_stolen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isSuccess = mutableStateOf(false)
    val isSuccess: State<Boolean> = _isSuccess

    fun reportStolen(imei: String, description: String, date: String, onFinished: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Simulating API call to flag device as stolen
            delay(2000)
            
            // In real app, we would send this to the server
            // For now we just mock success
            _isSuccess.value = true
            _isLoading.value = false
            
            delay(1500)
            onFinished()
        }
    }
}
