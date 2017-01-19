package com.felipecosta.kotlinrxjavasample

import android.app.Application
import android.content.Context
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.DaggerApplicationComponent
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

open class DemoApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = createComponent()
        initImageLoader()
    }

    private fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }

    open protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().build()
    }

    companion object {

        fun get(context: Context): DemoApplication {
            return context.applicationContext as DemoApplication
        }

    }
}