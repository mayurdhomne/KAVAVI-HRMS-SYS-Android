package com.hrms.clustorcomputing.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrms.clustorcomputing.data.model.request.BiometricRequest
import com.hrms.clustorcomputing.data.model.request.LoginRequest
import com.hrms.clustorcomputing.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            val loginRequest = LoginRequest(
                email = email,
                password = password,
                deviceId = "android_device", // Get actual device ID
                deviceName = android.os.Build.MODEL,
                rememberMe = false
            )
            
            authRepository.login(loginRequest).collect { result ->
                result.fold(
                    onSuccess = { authResponse ->
                        _loginState.value = LoginState.Success
                    },
                    onFailure = { exception ->
                        _loginState.value = LoginState.Error(
                            exception.message ?: "Login failed"
                        )
                    }
                )
            }
        }
    }

    fun biometricLogin() {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            // Get stored user data for biometric login
            val token = authRepository.getUserToken()
            if (token != null) {
                val biometricRequest = BiometricRequest(
                    userId = "stored_user_id", // Get from stored data
                    biometricType = "fingerprint", // or "face"
                    biometricData = "encrypted_data",
                    deviceId = "android_device"
                )
                
                authRepository.validateBiometric(biometricRequest).collect { result ->
                    result.fold(
                        onSuccess = { 
                            _loginState.value = LoginState.Success
                        },
                        onFailure = { exception ->
                            _loginState.value = LoginState.Error(
                                exception.message ?: "Biometric login failed"
                            )
                        }
                    )
                }
            } else {
                _loginState.value = LoginState.Error("No saved user data found")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect { result ->
                result.fold(
                    onSuccess = {
                        // Handle logout success
                    },
                    onFailure = { exception ->
                        // Handle logout error
                    }
                )
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}