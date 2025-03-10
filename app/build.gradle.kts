import org.jetbrains.kotlin.konan.properties.Properties

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("plugin.serialization") version Versions.kotlinVersion
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        buildConfigField(
            "String",
            "DAME_DAME_SERVER_BASE_URL_DEBUG",
            properties.getProperty("DAME_DAME_SERVER_BASE_URL_DEBUG")
        )
        buildConfigField(
            "String",
            "DAME_DAME_SERVER_BASE_URL_RELEASE",
            properties.getProperty("DAME_DAME_SERVER_BASE_URL_RELEASE")
        )
        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties.getProperty("KAKAO_NATIVE_APP_KEY")
        )
        manifestPlaceholders["NATIVE_APP_KEY"] =
            properties.getProperty("KAKAO_NATIVE_APP_KEY_NO_QUOTES")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    namespace = "com.yangbong.damedame"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":feature:notification"))
    implementation(project(":feature:write-diary"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:main"))
    implementation(project(":feature:set-profile"))
    implementation(project(":shared"))
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":navigator"))

    // Kotlin
    implementation(KotlinDependencies.kotlin)
    implementation(KotlinDependencies.kotlinxSerialization)
    implementation(KotlinDependencies.dateTime)

    // Android Core
    implementation(AndroidXDependencies.coreKtx)
    implementation(AndroidXDependencies.appCompat)
    implementation(AndroidXDependencies.constraintLayout)
    implementation(AndroidXDependencies.legacy)
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.paging3)
    implementation(AndroidXDependencies.splashScreen)

    // Material Design
    implementation(MaterialDesignDependencies.materialDesign)

    // Dagger-Hilt
    implementation(AndroidXDependencies.hilt)
    kapt(KaptDependencies.hiltCompiler)

    // Jetpack Navigation Component
    implementation(AndroidXDependencies.navigationFragment)
    implementation(AndroidXDependencies.navigationUI)

    // Jetpack Security
    implementation(AndroidXDependencies.security)

    // Jetpack Fragment
    implementation(AndroidXDependencies.fragment)

    // Jetpack Lifecycle
    implementation(AndroidXDependencies.coroutines)
    implementation(AndroidXDependencies.lifeCycleKtx)
    implementation(AndroidXDependencies.lifecycleJava8)

    // ImageLoading Library
    // Glide for general
    // Coil for compose
    implementation(ThirdPartyDependencies.coil)

    // Http Client Library
    implementation(ThirdPartyDependencies.retrofit)
    implementation(platform(ThirdPartyDependencies.okHttpBom))
    implementation(ThirdPartyDependencies.okHttp)
    implementation(ThirdPartyDependencies.okHttpLoggingInterceptor)
    implementation(ThirdPartyDependencies.kotlinxSerializationConverter)

    //JsonConverterLibrary
    implementation(ThirdPartyDependencies.gson)
    implementation(ThirdPartyDependencies.gsonConverter)

    // Logger - Timber
    implementation(ThirdPartyDependencies.timber)

    // Firebase
    implementation(platform(FirebaseDependency.firebaseBom))
    implementation(FirebaseDependency.analyticsKtx)
    implementation(FirebaseDependency.firebaeMessaging)

    // Automatic Record OpenSource Library List
    implementation(ThirdPartyDependencies.ossLicense)

    debugImplementation(ThirdPartyDependencies.leakCanary)

    // Kakao Login
    implementation(ThirdPartyDependencies.kakaoAuth)
}


