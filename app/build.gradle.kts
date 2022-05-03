plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.github.ked4ma.android_jetpack_compose_template"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        create("rc") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules-rc.pro",
                "proguard-rules.pro"
            )
        }
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // https://developer.android.com/studio/releases/gradle-plugin#j8-library-desugaring
    coreLibraryDesugaring(libs.tools.desugar.jdk)

    implementation(libs.bundles.android.base)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.bundles.compose.accompanist)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.oss.license.lib)

    testImplementation(libs.test.junit)
    testImplementation(libs.test.truth)
    androidTestImplementation(libs.test.androidx.ext.junit)
    androidTestImplementation(libs.test.androidx.espresso)
    androidTestImplementation(libs.test.androidx.compose.ui)
    androidTestImplementation(libs.test.truth)
}