plugins {
    id("application-module-plugin")
    id("kotlinx-serialization")
    id("com.vanniktech.dependency.graph.generator")
}

val MARVEL_PRIVATE_KEY: String by project
val MARVEL_PUBLIC_KEY: String by project

android {
    dynamicFeatures = mutableSetOf(":feature:detail", ":feature:listing", ":feature:wiki")

    defaultConfig {
        buildConfigField("String", "MARVEL_PRIVATE_KEY", "\"" + MARVEL_PRIVATE_KEY + "\"")
        buildConfigField("String", "MARVEL_PUBLIC_KEY", "\"" + MARVEL_PUBLIC_KEY + "\"")
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
