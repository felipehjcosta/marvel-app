package com.github.felipehjcosta.gradle.plugins

import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidBasicConfiguration
import com.github.felipehjcosta.gradle.plugins.extensions.applyAndroidDynamicFeaturePlugins
import com.github.felipehjcosta.gradle.plugins.extensions.applyDaggerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureModulePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            applyAndroidDynamicFeaturePlugins()
            applyAndroidBasicConfiguration {
                buildTypes {
                    create("debugMini") {
                        initWith(getByName("debug"))
                        matchingFallbacks = mutableListOf("debug")
                    }
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }
            }
            applyDaggerDependencies()
        }
    }

}