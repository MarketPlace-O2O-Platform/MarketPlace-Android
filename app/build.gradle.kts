import java.util.Properties

//val properties = Properties()
//properties.load(project.rootProject.file("local.properties").inputStream())

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("io.github.irgaly.compose-vector") version "1.0.1"
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.properties"
}

android {
    namespace = "dev.kichan.marketplace"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.kichan.marketplace"
        minSdk = 26
        targetSdk = 35
        versionCode = 4
        versionName = "1.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "KAKAO_REST_API_KEY", properties["KAKAO_REST_API_KEY"].toString())
        buildConfigField("String", "KAKAO_NATIVE_API_KEY", properties["KAKAO_NATIVE_API_KEY"].toString())
        buildConfigField("String", "KAKAO_ADMIN_API_KEY", properties["KAKAO_ADMIN_API_KEY"].toString())
        //buildConfigField("String", "API_BASE_URL", properties["API_BASE_URL"].toString())
        buildConfigField("String", "API_BASE_URL", properties["API_BASE_URL"]?.toString() ?: "\"https://default-url.com\"")

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
        buildConfig = true
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

composeVector {
    packageName = "dev.kichan.marketplace.ui.icon"
    inputDir = layout.projectDirectory.dir("images")
}

dependencies {
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.javafaker)

    implementation(libs.play.services.location)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.volley)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompat)

    implementation(platform("com.google.firebase:firebase-bom:33.10.0"))
    implementation("com.google.firebase:firebase-messaging:24.1.0")
    implementation("androidx.core:core-i18n:1.0.0-alpha01")
    val mapsComposeVersion = "4.4.1"
    implementation("com.google.maps.android:maps-compose:$mapsComposeVersion")
    // Google Maps Compose utility library
    implementation("com.google.maps.android:maps-compose-utils:$mapsComposeVersion")
    // Google Maps Compose widgets library
    implementation("com.google.maps.android:maps-compose-widgets:$mapsComposeVersion")

    implementation("io.coil-kt:coil-compose:2.2.2") // 최신 버전 확인 후 업데이트

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.kakao.map)
    implementation(libs.kakao.sdk.all)

    // Retorift
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}