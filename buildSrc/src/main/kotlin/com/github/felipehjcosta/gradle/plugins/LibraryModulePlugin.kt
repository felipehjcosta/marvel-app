package com.github.felipehjcosta.gradle.plugins

import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidLibraryPlugins
import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidBasicConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project

class LibraryModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyAndroidLibraryPlugins()
            applyAndroidBasicConfiguration {
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
                        isMinifyEnabled = true
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-rules-release.pro")
                    }
                }
            }
        }
    }

}