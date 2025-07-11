package com.hrms.clustorcomputing.data.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    
    @SerializedName("password")
    val password: String,
    
    @SerializedName("device_id")
    val deviceId: String? = null,
    
    @SerializedName("device_name")
    val deviceName: String? = null,
    
    @SerializedName("remember_me")
    val rememberMe: Boolean = false
)