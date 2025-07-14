package com.hrms.clustorcomputing.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecurePreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val PREFS_FILE_NAME = "secure_attendance_prefs"
    }

    private val masterKey: MasterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            PREFS_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        sharedPreferences.edit().putFloat(key, value).apply()
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }
}

// Extension functions for common auth-related preferences
private const val KEY_ACCESS_TOKEN = "access_token"
private const val KEY_REFRESH_TOKEN = "refresh_token"
private const val KEY_USER_ID = "user_id"
private const val KEY_USER_EMAIL = "user_email"
private const val KEY_USER_NAME = "user_name"
private const val KEY_LAST_LOGIN_TIME = "last_login_time"

fun SecurePreferences.saveAccessToken(token: String) = putString(KEY_ACCESS_TOKEN, token)
fun SecurePreferences.getAccessToken(): String? = getString(KEY_ACCESS_TOKEN).takeIf { it.isNotEmpty() }

fun SecurePreferences.saveRefreshToken(token: String) = putString(KEY_REFRESH_TOKEN, token)
fun SecurePreferences.getRefreshToken(): String? = getString(KEY_REFRESH_TOKEN).takeIf { it.isNotEmpty() }

fun SecurePreferences.saveUserId(userId: String) = putString(KEY_USER_ID, userId)
fun SecurePreferences.getUserId(): String? = getString(KEY_USER_ID).takeIf { it.isNotEmpty() }

fun SecurePreferences.saveUserEmail(email: String) = putString(KEY_USER_EMAIL, email)
fun SecurePreferences.getUserEmail(): String? = getString(KEY_USER_EMAIL).takeIf { it.isNotEmpty() }

fun SecurePreferences.saveUserName(name: String) = putString(KEY_USER_NAME, name)
fun SecurePreferences.getUserName(): String? = getString(KEY_USER_NAME).takeIf { it.isNotEmpty() }

fun SecurePreferences.saveLastLoginTime(timestamp: Long) = putLong(KEY_LAST_LOGIN_TIME, timestamp)
fun SecurePreferences.getLastLoginTime(): Long = getLong(KEY_LAST_LOGIN_TIME, 0L)

fun SecurePreferences.clearAll() = clear()