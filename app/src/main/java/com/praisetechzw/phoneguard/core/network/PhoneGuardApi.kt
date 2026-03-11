package com.praisetechzw.phoneguard.core.network

import com.google.gson.annotations.SerializedName

data class DeviceStatusResponse(
    @SerializedName("imei") val imei: String,
    @SerializedName("status") val status: String, // "CLEAN", "STOLEN", "LOST"
    @SerializedName("manufacturer") val manufacturer: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("report_date") val reportDate: String?
)

interface PhoneGuardApi {
    // Placeholder for IMEI check endpoint
    suspend fun checkImei(imei: String): DeviceStatusResponse
}
