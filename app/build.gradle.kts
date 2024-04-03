plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.animationapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.animationapp"
        minSdk = 16
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //region Google
    implementation(libs.material)
    //endregion
    //region Android X
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //endregion
    //region Android Unit Test and U.I. Test Library
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //endregion
}