package com.github.felipehjcosta.marvelapp.base

import android.app.Application
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class TestStubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }
}