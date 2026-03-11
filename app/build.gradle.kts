plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.paasswordmanager"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.paasswordmanager"
        minSdk = 24
        targetSdk = 36
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.biometric)
    implementation(libs.gson)
    
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.compiler)
    
    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

tasks.register("cleanupResources") {
    doLast {
        val dir = File(projectDir, "src/main/res/mipmap-anydpi-v26")
        if (dir.exists()) {
            dir.listFiles()?.forEach { file ->
                if (file.extension == "jpeg" || file.name.contains("copia")) {
                    if (file.delete()) {
                        println("Deleted: ${file.name}")
                    }
                }
            }
        }
    }
}
