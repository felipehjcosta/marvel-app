plugins {
    id("com.android.feature")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "com.github.felipehjcosta.marvelapp.wiki.runner.CustomTestRunner"

    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
        create("debugMini") {
            initWith(getByName("debug"))
            matchingFallbacks = listOf("debug")
        }
        getByName("release") {
            isMinifyEnabled = false
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

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }

    testOptions.unitTests.isIncludeAndroidResources = true

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
    }

}

dependencies {
    application(project(":apk"))
    implementation(project(":feature:base"))
    implementation("com.facebook.shimmer:shimmer:0.2.0")
    implementation(AndroidxDependencies.supportAnnotation)

    DaggerCompilerDependencies.values.forEach { kapt(it) }
    AnnotationsDependencies.values.forEach { compileOnly(it) }
}

val add_test_dependencies: groovy.lang.Closure<Any?> by extra
add_test_dependencies(project)
