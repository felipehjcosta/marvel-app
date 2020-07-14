plugins {
    id("library-module-plugin")
}

dependencies {
    implementation(AndroidxDependencies.appCompat)
    KotlinDependencies.values.forEach { implementation(it) }
}
