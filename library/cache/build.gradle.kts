plugins {
    id("library-module-plugin")
}

dependencies {
    implementation(AndroidxDependencies.appCompat)
    implementation(AndroidxDependencies.design)
    KotlinDependencies.values.forEach { implementation(it) }
    RxJavaDependencies.values.forEach { implementation(it) }
    DatabaseDependencies.values.forEach { api(it) }

    DatabaseCompilerDependencies.values.forEach { kapt(it) }

    testImplementation(TestDependencies.junit)
    AndroidTestDependencies.values.forEach { androidTestImplementation(it) }
}