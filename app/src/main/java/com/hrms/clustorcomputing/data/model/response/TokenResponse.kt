package com.hrms.clustorcomputing.data.model.response

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("success")
    val success: Boolean = false,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("access_token")
    val accessToken: String? = null,
    
    @SerializedName("refresh_token")
    val refreshToken: String? = null,
    
    @SerializedName("token_type")
    val tokenType: String? = "Bearer",
    
    @SerializedName("expires_in")
    val expiresIn: Long = 0
)