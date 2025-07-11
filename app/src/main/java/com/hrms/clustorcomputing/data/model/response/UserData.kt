package com.hrms.clustorcomputing.data.model.response

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id")
    val id: String? = null,
    
    @SerializedName("employee_id")
    val employeeId: String? = null,
    
    @SerializedName("name")
    val name: String? = null,
    
    @SerializedName("email")
    val email: String? = null,
    
    @SerializedName("phone")
    val phone: String? = null,
    
    @SerializedName("department")
    val department: String? = null,
    
    @SerializedName("designation")
    val designation: String? = null,
    
    @SerializedName("address")
    val address: String? = null,
    
    @SerializedName("profile_image")
    val profileImage: String? = null,
    
    @SerializedName("is_biometric_enabled")
    val isBiometricEnabled: Boolean = false,
    
    @SerializedName("created_at")
    val createdAt: String? = null,
    
    @SerializedName("updated_at")
    val updatedAt: String? = null
)