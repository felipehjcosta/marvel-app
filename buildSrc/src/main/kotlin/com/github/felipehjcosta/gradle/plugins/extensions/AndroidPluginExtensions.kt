package com.github.felipehjcosta.gradle.plugins.extensions

import Versions
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project

internal fun Project.applyAndroidBasicConfiguration(otherConfigurations: BaseExtension.() -> Unit = {}) {
    android.run {
        androidBasicConfiguration(otherConfigurations)
    }
}

private val Project.android: BaseExtension
    get() = extensions.findByName("android") as? BaseExtension
            ?: error("Not an Android module $name")

private fun BaseExtension.androidBasicConfiguration(otherConfigurations: BaseExtension.() -> Unit = {}) {
    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

    }

    lintOptions {
        // set to true to turn off analysis progress reporting by lint
        isQuiet = false
        // if true, stop the gradle build if errors are found
        isAbortOnError = true
        // if true, only report errors
        isIgnoreWarnings = false
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    packagingOptions {
        exclude("META-INF/app_debug.kotlin_module")
        exclude("META-INF/proguard/androidx-annotations.pro")
    }

    otherConfigurations(this)
}