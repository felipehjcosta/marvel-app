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

        register("library-module-plugin") {
            id = "library-module-plugin"
            implementationClass = "com.github.felipehjcosta.gradle.plugins.LibraryModulePlugin"
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

    implementation("com.android.tools.build:gradle:4.2.0-alpha10")
    implementation(kotlin("gradle-plugin", "1.4.10"))
}