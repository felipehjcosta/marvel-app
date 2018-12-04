plugins {
    id("com.android.application")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        applicationId = "com.github.felipehjcosta.marvelapp"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        create("debugMini") {
            initWith(getByName("debug"))
            isMinifyEnabled = true
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro", "proguard-rules-debug.pro")
            matchingFallbacks = listOf("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-rules-release.pro")
        }
    }

    lintOptions {
        // set to true to turn off analysis progress reporting by lint
        isQuiet = false
        // if true, stop the gradle build if errors are found
        isAbortOnError = true
        // if true, only report errors
        isIgnoreWarnings = false
    }
}

dependencies {
    implementation(project(":feature:base"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:listing"))
    implementation(project(":feature:wiki"))
}
