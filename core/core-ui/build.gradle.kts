plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)        // ✅ Required for Compose in this module
}

android {
    namespace = "com.example.core_ui"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        compose = true        // ✅ MUST ENABLE COMPOSE
    }

    composeOptions {
        // Will use Compose BOM, so version not needed here
    }
}

dependencies {
    // ----------------------
    // CORE Android
    // ----------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ----------------------
    // COMPOSE (using BOM)
    // ----------------------
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material Design 3
    implementation(libs.androidx.compose.material3)

    // Adaptive layout + navigation suite
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)

    // ----------------------
    // OPTIONAL UI COMPONENTS
    // ----------------------
    implementation(libs.androidx.activity.compose)

    // ----------------------
    // DEBUG-ONLY UI TOOLING
    // ----------------------
    debugImplementation(libs.androidx.compose.ui.tooling)

    // ----------------------
    // TESTS
    // ----------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
