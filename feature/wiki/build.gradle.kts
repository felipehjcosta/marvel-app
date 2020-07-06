plugins {
    id("feature-module-plugin")
}

dependencies {
    implementation(project(":feature:base"))
    implementation("com.facebook.shimmer:shimmer:0.2.0")
    implementation(AndroidxDependencies.supportAnnotation)
}

val add_test_dependencies: groovy.lang.Closure<Any?> by extra
add_test_dependencies(project)