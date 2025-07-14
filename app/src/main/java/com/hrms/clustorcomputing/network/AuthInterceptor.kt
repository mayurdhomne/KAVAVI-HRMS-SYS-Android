package com.hrms.clustorcomputing.network

import com.hrms.clustorcomputing.utils.SecurePreferences
import com.hrms.clustorcomputing.utils.getAccessToken
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val securePreferences: SecurePreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Get the access token from secure preferences
        val accessToken = securePreferences.getAccessToken()
        
        // If no token or this is an auth endpoint, proceed without token
        if (accessToken == null || accessToken.isEmpty() || isAuthEndpoint(originalRequest.url.encodedPath)) {
            return chain.proceed(originalRequest)
        }
        
        // Add Authorization header with Bearer token
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .build()
        
        return chain.proceed(authenticatedRequest)
    }
    
    private fun isAuthEndpoint(path: String): Boolean {
        return path.contains("/auth/login") || 
               path.contains("/auth/refresh-token") || 
               path.contains("/auth/forgot-password") || 
               path.contains("/auth/reset-password")
    }
}