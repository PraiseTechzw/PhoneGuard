package com.praisetechzw.phoneguard.feature.imei_check

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.DeviceRepository
import com.praisetechzw.phoneguard.core.network.DeviceStatusResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ImeiCheckState {
    object Idle : ImeiCheckState()
    object Loading : ImeiCheckState()
    data class Success(val result: DeviceStatusResponse) : ImeiCheckState()
    data class Error(val message: String) : ImeiCheckState()
}

@HiltViewModel
class ImeiCheckViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _state = mutableStateOf<ImeiCheckState>(ImeiCheckState.Idle)
    val state: State<ImeiCheckState> = _state

    fun checkImei(imei: String) {
        if (imei.length < 15) {
            _state.value = ImeiCheckState.Error("IMEI must be at least 15 digits")
            return
        }

        viewModelScope.launch {
            _state.value = ImeiCheckState.Loading
            
            // Simulating network delay
            delay(2000)
            
            try {
                // In a real app, this would use the network repository
                // For now we mock based on the input
                val status = if (imei.endsWith("99")) "stolen" else "clean"
                val response = DeviceStatusResponse(
                    imei = imei,
                    status = status,
                    brand = "Apple",
                    model = "iPhone 15 Pro",
                    reportDate = if (status == "stolen") "2024-05-10" else null
                )
                
                _state.value = ImeiCheckState.Success(response)
                
                // Save to history
                deviceRepository.checkDeviceStatus(imei)
            } catch (e: Exception) {
                _state.value = ImeiCheckState.Error("Failed to check device status: ${e.message}")
            }
        }
    }

    fun reset() {
        _state.value = ImeiCheckState.Idle
    }
}
