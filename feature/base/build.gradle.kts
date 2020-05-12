plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("com.vanniktech.dependency.graph.generator")
}

val MARVEL_PRIVATE_KEY: String by project
val MARVEL_PUBLIC_KEY: String by project

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    dynamicFeatures = mutableSetOf(":feature:detail", ":feature:listing", ":feature:wiki")

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)

        vectorDrawables.useSupportLibrary = true

        buildConfigField("String", "MARVEL_PRIVATE_KEY", "\"" + MARVEL_PRIVATE_KEY + "\"")
        buildConfigField("String", "MARVEL_PUBLIC_KEY", "\"" + MARVEL_PUBLIC_KEY + "\"")
    }

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
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":library:network"))
    implementation(project(":library:cache"))

    DebugDependencies.values.forEach { debugApi(it) }

    KotlinDependencies.values.forEach { api(it) }

    AndroidxDependencies.values.forEach { api(it) }

    DaggerDependencies.values.forEach { api(it) }
    DaggerCompilerDependencies.values.forEach { kapt(it) }
    AnnotationsDependencies.values.forEach { compileOnly(it) }

    ImageLoaderDependencies.values.forEach { api(it) }

    RxJavaDependencies.values.forEach { api(it) }

    UiDependencies.values.forEach { api(it) }

    NetworkDependencies.values.forEach { api(it) }
}

task("copyTestClasses", Copy::class) {
    from("build/tmp/kotlin-classes/debugUnitTest")
    into("build/intermediates/classes/debug")
}

val add_test_dependencies: groovy.lang.Closure<Any?> by extra
add_test_dependencies(project)
