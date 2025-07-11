package com.hrms.clustorcomputing.data.repository

import com.hrms.clustorcomputing.data.model.request.BiometricRequest
import com.hrms.clustorcomputing.data.model.request.LoginRequest
import com.hrms.clustorcomputing.data.model.response.AuthResponse
import com.hrms.clustorcomputing.data.model.response.BaseResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(request: LoginRequest): Flow<Result<AuthResponse>>
    suspend fun logout(): Flow<Result<BaseResponse>>
    suspend fun validateBiometric(request: BiometricRequest): Flow<Result<BaseResponse>>
    suspend fun refreshToken(): Flow<Result<AuthResponse>>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getUserToken(): String?
    suspend fun saveUserToken(token: String)
    suspend fun clearUserData()
}