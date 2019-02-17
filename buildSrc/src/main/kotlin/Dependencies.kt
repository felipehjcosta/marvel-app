object BuildPlugins {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
    const val kotlinSerializiationPlugin =
        "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    const val dependencyGraphGeneratorPlugin =
        "com.vanniktech:gradle-dependency-graph-generator-plugin:${Versions.dependencyGraphGeneratorPlugin}"
    const val androidJunitJacocoPlugin =
        "com.vanniktech:gradle-android-junit-jacoco-plugin:${Versions.androidJunitJacocoPlugin}"
    const val detektGradlePlugin =
        "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detektGradlePlugin}"
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

object AndroidxDependencies {
    const val androidxKtx = "androidx.core:core-ktx:${Versions.androidxKtxLibrary}"
    const val supportV4 = "androidx.legacy:legacy-support-v4:${Versions.androidxSupportV4Library}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompatLibrary}"
    const val design = "com.google.android.material:material:${Versions.androidxMaterialLibrary}"
    const val cardview = "androidx.cardview:cardview:1.0.0"
    const val recyclerView =
        "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerViewLibrary}"
    const val supportAnnotation =
        "androidx.annotation:annotation:${Versions.androidxSupportAnnotationLibrary}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayoutLibrary}"

    val values = listOf(
        androidxKtx,
        supportV4,
        appCompat,
        design,
        cardview,
        recyclerView,
        supportAnnotation,
        constraintLayout
    )
}

object DatabaseDependencies {
    const val room = "androidx.room:room-runtime:${Versions.roomLibrary}"
    const val roomRxjava2 = "androidx.room:room-rxjava2:${Versions.roomLibrary}"

    val values = listOf(room, roomRxjava2)
}

object DatabaseCompilerDependencies {
    const val room = "androidx.room:room-compiler:${Versions.roomLibrary}"

    val values = listOf(room)
}

object DatabaseTestDependencies {
    const val room = "androidx.room:room-testing:${Versions.roomLibrary}"

    val values = listOf(room)
}

object RxJavaDependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJavaLibrary}"
    const val rxJavaAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxJavaAndroidLibrary}"
    const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxJavaRelayLibrary}"
    const val rxBinding =
        "com.jakewharton.rxbinding3:rxbinding-core:${Versions.rxJavaBindingLibrary}"
    const val rxBindingDesign =
        "com.jakewharton.rxbinding3:rxbinding-material:${Versions.rxJavaBindingLibrary}"
    const val rxBindingSupportv4 =
        "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxJavaBindingLibrary}"
    const val a =
        "com.jakewharton.rxbinding3:rxbinding-swiperefreshlayout:${Versions.rxJavaBindingLibrary}"
    const val rxBindingRecyclerView =
        "com.jakewharton.rxbinding3:rxbinding-recyclerview:${Versions.rxJavaBindingLibrary}"
    const val rxAction = "com.github.felipehjcosta:rxaction:${Versions.rxActionLibrary}"

    val values = listOf(
        rxJava,
        rxJavaAndroid,
        rxRelay,
        rxBinding,
        rxBindingDesign,
        rxBindingSupportv4,
        rxBindingRecyclerView,
        a,
        rxAction
    )

}

object DaggerDependencies {
    const val dagger = "com.google.dagger:dagger:${Versions.daggerLibrary}"
    const val daggerAndroidSupport =
        "com.google.dagger:dagger-android-support:${Versions.daggerLibrary}"

    val values = listOf(dagger, daggerAndroidSupport)
}

object DaggerCompilerDependencies {
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.daggerLibrary}"
    const val daggerAndroidProcessor =
        "com.google.dagger:dagger-android-processor:${Versions.daggerLibrary}"

    val values = listOf(dagger, daggerAndroidProcessor)
}

object AnnotationsDependencies {
    const val javaxAnnotation = "javax.annotation:jsr250-api:${Versions.javaxAnnotationLibrary}"

    val values = listOf(javaxAnnotation)
}

object NetworkDependencies {
    const val okhHttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLibrary}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitLibrary}"
    const val retrofitAdapterRxJava2 =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitLibrary}"
    const val kotlinSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinSerializationConverterLibrary}"

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

object DebugDependencies {
    const val fragmentTesting =
        "androidx.fragment:fragment-testing:${Versions.androidxFragmentTesting}"

    val values = listOf(fragmentTesting)
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junitLibrary}"
    const val mockk = "io.mockk:mockk:${Versions.mockkLibrary}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricLibrary}"
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitLibrary}"
    const val androidxTruth = "androidx.test.ext:truth:${Versions.androidxTruthLibrary}"
    const val androidxRunner = "androidx.test:runner:${Versions.androidxRunnerLibrary}"
    const val androidxRules = "androidx.test:rules:${Versions.androidxRulesLibrary}"
    const val androidxEspressoCore =
        "androidx.test.espresso:espresso-core:${Versions.androidxEspressoCoreLibrary}"
    const val androidxEspressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.androidxEspressoContribLibrary}"

    val values = listOf(
        junit,
        mockk,
        robolectric,
        androidxJunit,
        androidxTruth,
        androidxRunner,
        androidxRules,
        androidxEspressoCore,
        androidxEspressoContrib
    )
}

object AndroidTestDependencies {
    const val androidxJunit = "androidx.test.ext:junit:${Versions.androidxJunitLibrary}"
    const val androidxTruth = "androidx.test.ext:truth:${Versions.androidxTruthLibrary}"
    const val androidxRunner = "androidx.test:runner:${Versions.androidxRunnerLibrary}"
    const val androidxRules = "androidx.test:rules:${Versions.androidxRulesLibrary}"
    const val androidxEspressoCore =
        "androidx.test.espresso:espresso-core:${Versions.androidxEspressoCoreLibrary}"
    const val androidxEspressoIntents =
        "androidx.test.espresso:espresso-intents:${Versions.androidxEspressoCoreLibrary}"
    const val androidxEspressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.androidxEspressoContribLibrary}"
    const val rx2Idler = "com.squareup.rx.idler:rx2-idler:${Versions.rx2IdlerLibrary}"
    const val testButler =
        "com.linkedin.testbutler:test-butler-library:${Versions.testButlerLibrary}"

    val values = listOf(
        androidxJunit,
        androidxRunner,
        androidxTruth,
        androidxRules,
        androidxEspressoCore,
        androidxEspressoIntents,
        androidxEspressoContrib,
        rx2Idler,
        testButler
    )
}

object AndroidTestUtilDependencies {
    const val testButlerApp =
        "com.linkedin.testbutler:test-butler-app:${Versions.testButlerLibrary}@apk"

    val values = listOf(testButlerApp)
}

object ImageLoaderDependencies {
    const val universalImageLoader =
        "com.nostra13.universalimageloader:universal-image-loader:${Versions.imageLoaderLibrary}"

    val values = listOf(universalImageLoader)
}

object UiDependencies {
    const val galleryLayoutManager =
        "com.github.felipehjcosta:gallerylayoutmanager:${Versions.galleryLayoutManagerLibrary}"
    const val recyclerViewDsl =
        "com.github.felipehjcosta:recyclerview-dsl:${Versions.recyclerViewDslLibrary}"

    val values = listOf(galleryLayoutManager, recyclerViewDsl)
}