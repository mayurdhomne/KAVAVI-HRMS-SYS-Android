package com.hrms.clustorcomputing.data.repository

import com.hrms.clustorcomputing.data.api.AuthApiService
import com.hrms.clustorcomputing.data.local.dao.UserDao
import com.hrms.clustorcomputing.data.model.request.BiometricRequest
import com.hrms.clustorcomputing.data.model.request.LoginRequest
import com.hrms.clustorcomputing.data.model.response.AuthResponse
import com.hrms.clustorcomputing.data.model.response.BaseResponse
import com.hrms.clustorcomputing.utils.SecurePreferences
import com.hrms.clustorcomputing.utils.saveLastLoginTime
import com.hrms.clustorcomputing.utils.saveUserId
import com.hrms.clustorcomputing.utils.saveUserEmail
import com.hrms.clustorcomputing.utils.saveUserName
import com.hrms.clustorcomputing.utils.getAccessToken
import com.hrms.clustorcomputing.utils.saveAccessToken
import com.hrms.clustorcomputing.utils.clearAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val userDao: UserDao,
    private val securePreferences: SecurePreferences
) : AuthRepository {

    override suspend fun login(request: LoginRequest): Flow<Result<AuthResponse>> = flow {
        try {
            val response = authApiService.login(request)
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                if (authResponse.success) {
                    authResponse.data?.token?.let { token: String -> 
                        saveUserToken(token)
                        securePreferences.saveLastLoginTime(System.currentTimeMillis())
                    }
                    authResponse.data?.user?.let { userData ->
                        // Save user data securely
                        userData.id?.let { id: String -> securePreferences.saveUserId(id) }
                        userData.email?.let { email: String -> securePreferences.saveUserEmail(email) }
                        userData.name?.let { name: String -> securePreferences.saveUserName(name) }
                        // Convert and save user to local database
                        // userDao.insertUser(userData.toUserEntity())
                    }
                    emit(Result.success(authResponse))
                } else {
                    emit(Result.failure(Exception(authResponse.message)))
                }
            } else {
                emit(Result.failure(Exception("Login failed")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun logout(): Flow<Result<BaseResponse>> = flow {
        try {
            val response = authApiService.logout()
            if (response.isSuccessful && response.body() != null) {
                clearUserData()
                emit(Result.success(response.body()!!))
            } else {
                clearUserData() // Clear data anyway
                emit(Result.success(BaseResponse(true, "Logged out locally")))
            }
        } catch (e: Exception) {
            clearUserData() // Clear data anyway
            emit(Result.success(BaseResponse(true, "Logged out locally")))
        }
    }

    override suspend fun validateBiometric(request: BiometricRequest): Flow<Result<BaseResponse>> = flow {
        try {
            val response = authApiService.validateBiometric(request)
            if (response.isSuccessful && response.body() != null) {
                emit(Result.success(response.body()!!))
            } else {
                emit(Result.failure(Exception("Biometric validation failed")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun refreshToken(): Flow<Result<AuthResponse>> = flow {
        try {
            val response = authApiService.refreshToken()
            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!
                if (authResponse.success) {
                    authResponse.data?.token?.let { saveUserToken(it) }
                    emit(Result.success(authResponse))
                } else {
                    emit(Result.failure(Exception(authResponse.message)))
                }
            } else {
                emit(Result.failure(Exception("Token refresh failed")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return securePreferences.getAccessToken() != null
    }

    override suspend fun getUserToken(): String? {
        return securePreferences.getAccessToken()
    }

    override suspend fun saveUserToken(token: String) {
        securePreferences.saveAccessToken(token)
    }

    override suspend fun clearUserData() {
        securePreferences.clearAll()
        userDao.deleteAllUsers()
    }
}