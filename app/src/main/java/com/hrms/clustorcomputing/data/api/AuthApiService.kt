package com.hrms.clustorcomputing.data.api

import com.hrms.clustorcomputing.data.model.request.BiometricRequest
import com.hrms.clustorcomputing.data.model.request.LoginRequest
import com.hrms.clustorcomputing.data.model.response.AuthResponse
import com.hrms.clustorcomputing.data.model.response.BaseResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): Response<BaseResponse>
    
    @POST("auth/refresh-token")
    suspend fun refreshToken(): Response<AuthResponse>
    
    @POST("auth/biometric/validate")
    suspend fun validateBiometric(@Body request: BiometricRequest): Response<BaseResponse>
    
    @POST("auth/biometric/register")
    suspend fun registerBiometric(@Body request: BiometricRequest): Response<BaseResponse>
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body email: Map<String, String>): Response<BaseResponse>
    
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: Map<String, String>): Response<BaseResponse>
    
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<AuthResponse>
}