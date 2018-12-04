plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        create("debugMini") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro", "proguard-rules-debug.pro")
            matchingFallbacks = listOf("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    tasks.getByName("lint").enabled = false

    adbOptions {
        timeOutInMs = 5 * 60 * 1000
    }

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
    }
}

dependencies {
    implementation(SupportDependencies.appCompat)
    implementation(SupportDependencies.design)
    KotlinDependencies.values.forEach { implementation(it) }
    RxJavaDependencies.values.forEach { implementation(it) }
    DatabaseDependencies.values.forEach { api(it) }

    DatabaseCompilerDependencies.values.forEach { kapt(it) }

    androidTestImplementation(AndroidTestDependencies.runner)
    androidTestImplementation(AndroidTestDependencies.junit)
    androidTestImplementation(AndroidTestDependencies.expressoIntents)
    androidTestImplementation(AndroidTestDependencies.expressoCore)
    androidTestImplementation(AndroidTestDependencies.truth)
    testImplementation(TestDependencies.junit)
}