plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)

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
            matchingFallbacks = mutableListOf("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    tasks.getByName("lint").enabled = false

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
    }
}

dependencies {
    implementation(AndroidxDependencies.appCompat)
    implementation(AndroidxDependencies.design)
    KotlinDependencies.values.forEach { implementation(it) }
    RxJavaDependencies.values.forEach { implementation(it) }
    DatabaseDependencies.values.forEach { api(it) }

    DatabaseCompilerDependencies.values.forEach { kapt(it) }

    testImplementation(TestDependencies.junit)
    AndroidTestDependencies.values.forEach { androidTestImplementation(it) }
}