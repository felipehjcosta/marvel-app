package com.github.felipehjcosta.gradle.plugins

import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidApplicationPlugins
import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidBasicConfiguration
import com.github.felipehjcosta.gradle.plugins.extensions.applyDaggerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplicationModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyAndroidApplicationPlugins()
            applyAndroidBasicConfiguration {
                buildTypes {
                    create("debugMini") {
                        initWith(getByName("debug"))
                        isMinifyEnabled = true
                        isDebuggable = true
                        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro", "proguard-rules-debug.pro")
                        setMatchingFallbacks("debug")
                    }
                    getByName("release") {
                        isMinifyEnabled = true
                        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro", "proguard-rules-release.pro")
                    }
                }
            }
            applyDaggerDependencies()
        }
    }

}