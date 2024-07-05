plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    //deprecated
    //google gms service
    //id("com.google.gms.google-services")

    //deprecated
    //tencent gms service
    //id("com.tencent.android.tpns")
}

android {
    namespace = "com.huiiro.ncn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.huiiro.ncn"
        minSdk = 31
        targetSdk = 34
        versionCode = 8
        versionName = "1.0.8"

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
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)

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

    //http
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter.gson)
    implementation(libs.squareup.retrofit2.adapter.rxjava2)

    //ui
    implementation(libs.qmui)

    //kv store
    implementation(libs.mmkv)

    //permission
    implementation(libs.permissionx)

    //circleIndicator
    implementation(libs.circleindicator)

    //gson
    implementation(libs.gson)

    //tal layout
    implementation(libs.dsl.tab.layout)
    implementation(libs.dsl.tab.viewpager)

    //worker
    implementation(libs.work.runtime)

    //coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    //splash
    implementation(libs.splashccreen)

    //chart
    implementation(libs.mp.android.chart)

    //viewAdapter
    implementation(libs.cymchad)

    //web-image
    implementation(libs.glide)

    // gms firebase
    // implementation(platform(libs.firebase.bom))
    // implementation(libs.firebase.analytics)
}