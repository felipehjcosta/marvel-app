package com.github.felipehjcosta.appdetail

import android.app.Application
//import com.felipecosta.kotlinrxjavasample.R
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class TestStubApplication : Application() {
    override fun onCreate() {
        setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar)
        super.onCreate()
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }
}