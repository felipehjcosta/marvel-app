object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kotlinSerializiationPlugin = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    const val dependencyGraphGeneratorPlugin = "com.vanniktech:gradle-dependency-graph-generator-plugin:${Versions.dependencyGraphGeneratorPlugin}"
    const val androidJunitJacocoPlugin = "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.androidJunitJacocoPlugin}"
}

object KotlinDependencies {
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Kotlin.version}"

    val values = listOf(kotlinStdlib)
}

object KotlinTestDependencies {
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Kotlin.version}"
    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Kotlin.version}"

    val values = listOf(kotlinTest, kotlinTestJunit)
}

object SupportDependencies {
    const val supportV4 = "com.android.support:support-v4:${Versions.supportLibrary}"
    const val appCompat = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    const val design = "com.android.support:design:${Versions.supportLibrary}"
    const val recyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
    const val cardView = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    const val supportAnnotation = "com.android.support:support-annotations:${Versions.supportLibrary}"
    const val constraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"

    val values = listOf(
            supportV4,
            appCompat,
            design,
            recyclerView,
            cardView,
            supportAnnotation,
            constraintLayout
    )
}

object DatabaseDependencies {
    const val room = "android.arch.persistence.room:runtime:${Versions.roomLibrary}"
    const val roomRxjava2 = "android.arch.persistence.room:rxjava2:${Versions.roomLibrary}"

    val values = listOf(room, roomRxjava2)
}

object DatabaseCompilerDependencies {
    const val room = "android.arch.persistence.room:compiler:${Versions.roomLibrary}"

    val values = listOf(room)
}

object DatabaseTestDependencies {
    const val room = "android.arch.persistence.room:testing:${Versions.roomLibrary}"

    val values = listOf(room)
}
