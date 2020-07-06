package com.github.felipehjcosta.gradle.plugins.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies

internal fun Project.applyDaggerDependencies() {
    dependencies {
        DaggerDependencies.values.forEach { implementation(it) }
        DaggerCompilerDependencies.values.forEach { kapt(it) }
        AnnotationsDependencies.values.forEach { compileOnly(it) }
    }
}

internal fun DependencyHandlerScope.implementation(depName: String) {
    add("implementation", depName)
}

internal fun DependencyHandlerScope.kapt(depName: String) {
    add("kapt", depName)
}

internal fun DependencyHandlerScope.compileOnly(depName: String) {
    add("compileOnly", depName)
}

internal fun DependencyHandlerScope.api(depName: String) {
    add("api", depName)
}