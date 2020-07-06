plugins {
    id("feature-module-plugin")
}

dependencies {
    implementation(project(":feature:base"))
}

val add_test_dependencies: groovy.lang.Closure<Any?> by extra
add_test_dependencies(project)
