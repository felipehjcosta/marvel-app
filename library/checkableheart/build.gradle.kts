plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    buildToolsVersion(Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        vectorDrawables.useSupportLibrary = true

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    tasks.getByName("lint").enabled = false

    packagingOptions {
        exclude("META-INF/proguard/androidx-annotations.pro")
    }

}

dependencies {
    implementation(SupportDependencies.appCompat)
    implementation(SupportDependencies.design)

    testImplementation(TestDependencies.junit)

    AndroidTestDependencies.values.forEach { androidTestImplementation(it) }
}
