plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.manoj.todocompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.manoj.todocompose"
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
    hilt {
        enableAggregatingTask = true
    }
}


dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.material3)
    implementation(Dependencies.materialIconsExtended)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.lifecycleLivedataKtx)
    implementation(Dependencies.lifecycleViewmodelKtx)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUiKtx)
    implementation(Dependencies.legacySupportV4)
    implementation(Dependencies.foundationAndroid)
    implementation(Dependencies.material3Android)
    implementation(Dependencies.roomKtx)
    implementation(Dependencies.roomRuntime)
    annotationProcessor(Dependencies.roomCommon)
    kapt(Dependencies.roomCompiler)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.espressoCore)
    implementation(Dependencies.uiToolingPreviewAndroid)


    //Hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltAndroidCompiler)
    implementation(Dependencies.hiltNavigationCompose)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.gsonConverter)

    implementation(Dependencies.okHttp)
    implementation(Dependencies.loggingInterceptor)

}
kapt {
    correctErrorTypes = true
}