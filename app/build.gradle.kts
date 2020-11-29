plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Android.compileSdk)

    defaultConfig {
        applicationId = "io.viewpoint.giphy"
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "API_KEY", "\"${External.apiKey}\"")
        }
        getByName("release") {
            buildConfigField("String", "API_KEY", "\"${External.apiKey}\"")

            minifyEnabled(false)
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Kotlin.stdLib)

    // DI
    implementation(Libraries.dagger)
    implementation(Libraries.hilt)
    implementation(AndroidX.hiltLifecycle)
    kapt(Libraries.dagger_kapt)
    kapt(Libraries.hilt_kapt)
    kapt(AndroidX.hiltKapt)

    // androidx or google
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(Libraries.material)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.lifecycleViewmodel)
    implementation(AndroidX.lifecycleLivedata)
    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.paging)

    // images
    implementation(Libraries.glide)
    kapt(Libraries.glideCompiler)
    implementation(Libraries.shimmer)

    // logging
    implementation(Libraries.timber)

    testImplementation(TestLibraries.junit)

    androidTestImplementation(TestLibraries.androidTestJunit)
    androidTestImplementation(TestLibraries.androidTestEspresso)
}