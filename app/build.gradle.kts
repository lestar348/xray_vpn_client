plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.synaptic.xcorevpn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.synaptic.xcorevpn"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = false
            ndk {
                abiFilters += listOf("armeabi-v7a","arm64-v8a","x86","x86_64")
            }
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = false
            ndk {
                abiFilters += listOf("armeabi-v7a","arm64-v8a","x86","x86_64")
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    sourceSets {
        named("main") {
            //jniLibs.srcDirs("libs")
            java.srcDirs ("src/main/kotlin")
        }
    }



//    splits {
//        abi {
//            isEnable = true
//            reset()
//            include("x86", "x86_64", "armeabi-v7a", "arm64-v8a") //select ABIs to build APKs for
//            isUniversalApk = true //generate an additional APK that contains all the ABIs
//        }
//    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }

    //appcompat  = "com.synaptic.xcorevpn"
}

dependencies {
    // Native libs
    //implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar", "*.so"), "exclude" to listOf<String>())))

    implementation(project(mapOf("path" to ":vpn_core_lib")))
    implementation ("androidx.appcompat:appcompat:1.6.1")

    // Kotlin
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.2")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${rootProject.extra["lifecycle_version"]}")

    // Json converter
    implementation ("com.google.code.gson:gson:2.10.1")

    // Key-value storage
    implementation ("com.tencent:mmkv-static:1.2.15")

    //
    implementation ("io.reactivex:rxjava:1.3.8")
    implementation ("io.reactivex:rxandroid:1.2.1")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}