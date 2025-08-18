import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Dagger Hilt
    id("dagger.hilt.android.plugin")
    // KSP
    id("com.google.devtools.ksp")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.stockflow"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.stockflow"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "FIREBASE_TOKEN", getToken())
        buildConfigField("String", "BASE_URL", getBaseUrlInCIEnvironment())
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
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    //dagger-hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.gradle.plugin)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //retrofit & json converter
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //FireBase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth.ktx) //Firebase Authentication

    //Google Sign-In option
    implementation (libs.play.services.auth)

    //okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Extended Material Components
    implementation(libs.androidx.material.icons.extended)

    // Coil
    implementation(libs.coil.compose)

    //Swipe Refresh
    implementation(libs.accompanist.swiperefresh)

}

fun getToken(): String{
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("FIREBASE_TOKEN")
}

fun getBaseUrlInCIEnvironment(): String {
    val propFile = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties.getProperty("BASE_URL")
}