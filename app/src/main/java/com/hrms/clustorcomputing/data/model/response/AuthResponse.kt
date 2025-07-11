package com.hrms.clustorcomputing.data.model.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean = false,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("data")
    val data: AuthData? = null
)

data class AuthData(
    @SerializedName("token")
    val token: String? = null,
    
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    
    @SerializedName("expires_in")
    val expiresIn: Long = 0,
    
    @SerializedName("user")
    val user: UserData? = null
)