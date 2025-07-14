plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.hrms.clustorcomputing"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hrms.clustorcomputing"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.core.splashscreen)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Lifecycle & ViewModels
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.core)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.8.1")

    // Biometric
    implementation(libs.biometric)

    // Location Services
    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)

    // CameraX
    implementation(libs.camerax.core)
    implementation(libs.camerax.camera2)
    implementation(libs.camerax.lifecycle)
    implementation(libs.camerax.view)

    // Image Loading
    implementation(libs.glide)

    //Refresh Layout
    implementation(libs.androidx.swiperefreshlayout)

    // WorkManager
    implementation(libs.work.runtime)

    // Security & Encrypted Storage
    implementation(libs.security.crypto)
    implementation(libs.datastore.preferences)

    // Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}