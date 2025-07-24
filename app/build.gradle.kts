plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.7"
}

android {
    namespace = "uk.co.devfoundry.moodselector"
    compileSdk = 35

    defaultConfig {
        applicationId = "uk.co.devfoundry.moodselector"
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

    buildFeatures {
        compose = true
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        html.required.set(true)
        xml.required.set(false)
    }

    classDirectories.setFrom(
        fileTree("${buildDir}/intermediates/classes/debug") {
            exclude(
                "**/R.class",              // Android's generated resources class
                "**/R$*.class",            // Inner resource classes (e.g. R$drawable)
                "**/BuildConfig.*",        // Generated build config
                "**/Manifest*.*",          // Manifest stubs
                "**/*Test*.*"              // Your test classes
            )
        }
    )

    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(files("${buildDir}/jacoco/testDebugUnitTest.exec"))
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Unit Testing (JVM tests)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation("org.mockito:mockito-core:4.11.0")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

    // Instrumented Android UI Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging Tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
