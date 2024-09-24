@file:Suppress("UnstableApiUsage")

/*
* Copyright 2024 Wultra s.r.o.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions
* and limitations under the License.
*/

plugins {
    id("com.android.library")
    kotlin("android")
    id("org.jetbrains.dokka")
}

android {

    namespace = "com.wultra.android.utilities"

    compileSdk = 33
    buildToolsVersion = "33.0.2"

    defaultConfig {
        minSdk = 21
        @Suppress("DEPRECATION")
        targetSdk = 33

        // since Android Gradle Plugin 4.1.0
        // VERSION_CODE and VERSION_NAME are not generated for libraries
        buildConfigField("Integer", "VERSION_CODE", "1")
        buildConfigField("String", "VERSION_NAME", "\"${properties["VERSION_NAME"] as String}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    // TODO: do we need it?
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
}

apply("android-release-aar.gradle")
