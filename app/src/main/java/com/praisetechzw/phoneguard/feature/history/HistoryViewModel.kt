package com.praisetechzw.phoneguard.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praisetechzw.phoneguard.core.data.repository.DeviceRepository
import com.praisetechzw.phoneguard.core.database.PhoneReportEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    val historyEntries: StateFlow<List<PhoneReportEntity>> = deviceRepository.getCheckHistory()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
