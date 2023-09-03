plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.github.dzkoirn.malevich.android"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    namespace = "io.github.dzkoirn"
}

dependencies {
    implementation(project(":common"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
