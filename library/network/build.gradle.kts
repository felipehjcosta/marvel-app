plugins {
    id("library-module-plugin")
}

dependencies {
    KotlinDependencies.values.forEach { implementation(it) }
    NetworkDependencies.values.forEach { implementation(it) }
    implementation(RxJavaDependencies.rxJava)
}

val add_test_dependencies: groovy.lang.Closure<Any?> by extra
add_test_dependencies(project)