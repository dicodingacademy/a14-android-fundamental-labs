plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.dicoding.picodiploma.mybackgroundthread"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.dicoding.picodiploma.mybackgroundthread"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(listOf(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}