package com.praisetechzw.phoneguard.core.data.repository

import com.praisetechzw.phoneguard.core.common.Resource
import com.praisetechzw.phoneguard.core.database.PhoneReportDao
import com.praisetechzw.phoneguard.core.database.PhoneReportEntity
import com.praisetechzw.phoneguard.core.network.PhoneGuardApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepository @Inject constructor(
    private val api: PhoneGuardApi,
    private val dao: PhoneReportDao
) {
    fun checkDeviceStatus(imei: String): Flow<Resource<PhoneReportEntity>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.checkImei(imei)
            val entity = PhoneReportEntity(
                imei = response.imei,
                manufacturer = response.manufacturer ?: "Unknown",
                model = response.model ?: "Unknown",
                status = response.status,
                timestamp = System.currentTimeMillis()
            )
            // Save to history
            dao.insertReport(entity)
            emit(Resource.Success(entity))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }

    fun getCheckHistory(): Flow<List<PhoneReportEntity>> {
        return dao.getReportsFlow()
    }

    suspend fun getHistory(): List<PhoneReportEntity> {
        return dao.getAllReports()
    }
    
    suspend fun saveOwnedDevice(imei: String, manufacturer: String, model: String) {
        dao.insertReport(
            PhoneReportEntity(
                imei = imei,
                manufacturer = manufacturer,
                model = model,
                status = "OWNED",
                isUserOwned = true
            )
        )
    }
}
