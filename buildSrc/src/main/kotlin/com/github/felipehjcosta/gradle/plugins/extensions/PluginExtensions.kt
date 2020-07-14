package com.github.felipehjcosta.gradle.plugins.extensions

import org.gradle.api.Project
import org.gradle.api.plugins.ObjectConfigurationAction

internal fun Project.applyAndroidApplicationPlugins() {
    apply {
        androidApplicationPlugin()
        kotlinAndroidPlugin()
        kotlinKaptPlugin()
    }
}

internal fun Project.applyAndroidDynamicFeaturePlugins() {
    apply {
        androidDynamicFeaturePlugin()
        kotlinAndroidPlugin()
        kotlinKaptPlugin()
    }
}

internal fun Project.applyAndroidLibraryPlugins() {
    apply {
        androidLibraryPlugin()
        kotlinAndroidPlugin()
        kotlinKaptPlugin()
    }
}

private fun ObjectConfigurationAction.androidApplicationPlugin() = plugin("com.android.application")

private fun ObjectConfigurationAction.androidDynamicFeaturePlugin() = plugin("com.android.dynamic-feature")

private fun ObjectConfigurationAction.androidLibraryPlugin() = plugin("com.android.library")

private fun ObjectConfigurationAction.kotlinAndroidPlugin() = plugin("kotlin-android")

private fun ObjectConfigurationAction.kotlinKaptPlugin() = plugin("kotlin-kapt")