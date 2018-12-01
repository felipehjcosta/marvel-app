object BuildPlugins {
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
}

object Dependencies {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Kotlin.version}"
}

object KotlinDependencies {
    val values = listOf(Dependencies.kotlinStdlib)
}
