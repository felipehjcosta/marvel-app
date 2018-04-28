package com.github.felipehjcosta.marvelapp.wiki

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class TestStubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar)
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }
}