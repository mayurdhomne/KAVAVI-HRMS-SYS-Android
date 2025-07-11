package com.hrms.clustorcomputing.data.model.request

import com.google.gson.annotations.SerializedName

data class BiometricRequest(
    @SerializedName("user_id")
    val userId: String,
    
    @SerializedName("biometric_data")
    val biometricData: String,
    
    @SerializedName("biometric_type")
    val biometricType: String, // "fingerprint", "face", "voice"
    
    @SerializedName("device_id")
    val deviceId: String? = null,
    
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)