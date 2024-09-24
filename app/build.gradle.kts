plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("com.google.dagger.hilt.android")
    id ("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

android {
    namespace = "uz.turgunboyevjurabek.noteapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "uz.turgunboyevjurabek.noteapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    /**
     * For Navigation
     */
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    /**
     * Dagger-hilt
     */
    implementation ("com.google.dagger:hilt-android:2.49")
    ksp ("com.google.dagger:hilt-android-compiler:2.48")
    implementation ("androidx.hilt:hilt-navigation-fragment:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    /**
     * ViewModel
     */
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")


    /**
     * Kotlin Coroutines
     */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    /**
     * Room database
     */
    implementation ("androidx.room:room-runtime:2.6.0-alpha01")
    ksp ("androidx.room:room-compiler:2.6.0-alpha01")
    implementation("androidx.room:room-ktx:2.6.1")

    /**
     * DataStore
     */
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    /**
     * ConstraintLayout
     */
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    /**
     * Coil
     */
    implementation("io.coil-kt:coil:2.3.0") // Core library
    implementation("io.coil-kt:coil-compose:2.3.0") // for Jetpack Compose
}