plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mannatsandroidlab"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mannatsandroidlab"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            matchingFallbacks += listOf("release")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("NOTICES/libcore-NOTICES.txt")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat) // Ensure this is defined in libs.versions.toml
    implementation(libs.material) // Ensure this is defined in libs.versions.toml
    implementation(libs.constraintlayout) // Ensure this is defined in libs.versions.toml
    testImplementation(libs.ext.junit) // Ensure this is defined in libs.versions.toml
    androidTestImplementation(libs.espresso.core) // Ensure this is defined in libs.versions.toml

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.8")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation("com.android.volley:volley:1.2.1")
}
