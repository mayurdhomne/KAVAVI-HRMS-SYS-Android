package com.hrms.clustorcomputing.data.api

import com.hrms.clustorcomputing.data.model.request.*
import com.hrms.clustorcomputing.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Attendance endpoints
    @POST("attendance/checkin")
    suspend fun checkIn(@Body request: CheckInRequest): Response<AttendanceResponse>
    
    @POST("attendance/checkout")
    suspend fun checkOut(@Body request: CheckOutRequest): Response<AttendanceResponse>
    
    @GET("attendance/history")
    suspend fun getAttendanceHistory(
        @Query("userId") userId: String,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Response<AttendanceHistoryResponse>
    
    @GET("attendance/today")
    suspend fun getTodayAttendance(@Query("userId") userId: String): Response<AttendanceResponse>
    
    @POST("attendance/emergency-checkin")
    suspend fun emergencyCheckIn(@Body request: EmergencyCheckInRequest): Response<AttendanceResponse>
    
    // User endpoints
    @GET("user/profile")
    suspend fun getUserProfile(@Query("userId") userId: String): Response<UserProfileResponse>
    
    @PUT("user/profile")
    suspend fun updateUserProfile(@Body request: UpdateProfileRequest): Response<UserProfileResponse>
    
    @POST("user/biometric/enable")
    suspend fun enableBiometric(@Body request: BiometricRequest): Response<BaseResponse>
    
    @POST("user/biometric/disable")
    suspend fun disableBiometric(@Body request: BiometricRequest): Response<BaseResponse>
    
    // Location endpoints
    @GET("locations/office")
    suspend fun getOfficeLocations(): Response<LocationResponse>
    
    @POST("locations/validate")
    suspend fun validateLocation(@Body request: LocationValidationRequest): Response<LocationValidationResponse>
    
    // Dashboard endpoints
    @GET("dashboard/summary")
    suspend fun getDashboardSummary(@Query("userId") userId: String): Response<DashboardSummaryResponse>
    
    @GET("dashboard/monthly-stats")
    suspend fun getMonthlyStats(
        @Query("userId") userId: String,
        @Query("month") month: String,
        @Query("year") year: String
    ): Response<MonthlyStatsResponse>
}