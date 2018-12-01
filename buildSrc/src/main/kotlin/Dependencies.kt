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

object RxJavaDependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaLibrary}"
    const val rxJavaAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroidLibrary}"
    const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxJavaRelayLibrary}"
    const val rxBinding = "com.jakewharton.rxbinding2:rxbinding-kotlin:${Versions.rxJavaBindingLibrary}"
    const val rxBindingDesign = "com.jakewharton.rxbinding2:rxbinding-design-kotlin:${Versions.rxJavaBindingLibrary}"
    const val rxBindingSupportv4 = "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${Versions.rxJavaBindingLibrary}"
    const val rxBindingRecyclerView = "com.jakewharton.rxbinding2:rxbinding-recyclerview-v7-kotlin:${Versions.rxJavaBindingLibrary}"
    const val rxAction = "com.github.felipehjcosta:rxaction:${Versions.rxActionLibrary}"

    val values = listOf(
            rxJava,
            rxJavaAndroid,
            rxRelay,
            rxBinding,
            rxBindingDesign,
            rxBindingSupportv4,
            rxBindingRecyclerView,
            rxAction
    )

}

object DaggerDependencies {
    const val dagger = "com.google.dagger:dagger:${Versions.daggerLibrary}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.daggerLibrary}"

    val values = listOf(dagger, daggerAndroidSupport)
}

object DaggerCompilerDependencies {
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.daggerLibrary}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.daggerLibrary}"

    val values = listOf(dagger, daggerAndroidProcessor)
}

object AnnotationsDependencies {
    const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxAnnotationLibrary}"

    val values = listOf(javaxAnnotation)
}

object NetworkDependencies {
    const val okhHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLibrary}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitLibrary}"
    const val retrofitAdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitLibrary}"
    const val kotlinSerializationConverter = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverterLibrary}"

    val values = listOf(
            okhHttpLogging,
            retrofit,
            retrofitAdapterRxJava2,
            kotlinSerializationConverter
    )
}

object NetworkTestDependencies {
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpLibrary}"

    val values = listOf(mockWebServer)
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junitLibrary}"
    const val mockk = "io.mockk:mockk:${Versions.mockkLibrary}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricLibrary}"
    const val robolectricSupport = "org.robolectric:shadows-supportv4:${Versions.robolectricLibrary}"

    val values = listOf(
            junit,
            mockk,
            robolectric,
            robolectricSupport
    )
}

object AndroidTestDependencies {
    const val runner = "androidx.test:runner:${Versions.androidTestLibrary}"
    const val junit = "androidx.test.ext:junit:${Versions.androidTestJunitLibrary}"
    const val expressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoLibrary}"
    const val expressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoLibrary}"
    const val truth = "androidx.test.ext:truth:${Versions.androidTestTruthLibrary}"
    const val rx2Idler = "com.squareup.rx.idler:rx2-idler:${Versions.rx2IdlerLibrary}"
    const val testButler = "com.linkedin.testbutler:test-butler-library:${Versions.testButlerLibrary}"

    val values = listOf(
            runner,
            junit,
            expressoCore,
            expressoIntents,
            truth,
            rx2Idler,
            testButler
    )
}

object AndroidTestUtilDependencies {
    const val testButlerApp = "com.linkedin.testbutler:test-butler-app:${Versions.testButlerLibrary}@apk"

    val values = listOf(testButlerApp)
}