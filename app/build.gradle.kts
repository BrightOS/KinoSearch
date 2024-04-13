import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.navigation.safe.args)
    alias(libs.plugins.google.devtools.ksp)
    kotlin("kapt")
}

android {
    namespace = "ru.bashcony.kinosearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.bashcony.kinosearch"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = "18"
        freeCompilerArgs = listOf("-Xallow-result-return-type")
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.epoxy)
    implementation(libs.epoxy.paging3)
    implementation(libs.lottie)
    implementation(libs.shimmer)
    implementation(libs.glide)
    implementation(libs.material)
    implementation(libs.app.update.ktx)
    implementation(libs.hilt.android)
    implementation(libs.gson)
    implementation(libs.zoomage)
    implementation(libs.paging.rxjava2)
    implementation(libs.circleimageview)
    implementation(libs.joda.convert)
    implementation(libs.joda.time)
    implementation(libs.kotlin.reflect)
    implementation(libs.jsoup)
    implementation(libs.rxandroid)
    implementation(libs.rxjava)
    implementation(libs.rxkotlin)
    implementation(libs.androidx.work.runtime.ktx)
    kapt(libs.ksp.hilt)
    kapt(libs.ksp.epoxy)
    kapt(libs.ksp.glide)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}