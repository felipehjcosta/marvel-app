import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("feature-module-plugin") {
            id = "feature-module-plugin"
            implementationClass = "com.github.felipehjcosta.gradle.plugins.FeatureModulePlugin"
        }

        register("application-module-plugin") {
            id = "application-module-plugin"
            implementationClass = "com.github.felipehjcosta.gradle.plugins.ApplicationModulePlugin"
        }
    }
}

repositories {
    jcenter()
    google()
}

dependencies {
    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:4.1.0-beta02")
    implementation(kotlin("gradle-plugin", "1.3.61"))
}