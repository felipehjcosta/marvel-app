package com.felipecosta.kotlinrxjavasample

import android.app.Application
import com.felipecosta.kotlinrxjavasample.di.*
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import javax.inject.Provider
import kotlin.reflect.KClass

open class DemoApplication : Application(), HasSubcomponentBuilders {

    lateinit var subcomponentBuilders: Map<Class<*>, Provider<SubcomponentBuilder<*>>>

    override fun onCreate() {
        super.onCreate()
        val component = createComponent()
        subcomponentBuilders = component.subcomponentBuidlers()
        initImageLoader()
    }

    private fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }

    open protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().appModule(AppModule(this)).build()
    }

    override fun <A : SubcomponentBuilder<*>> getSubcomponentBuilder(componentClass: KClass<A>): A {
        return componentClass.java.cast(subcomponentBuilders[componentClass.java]?.get())
    }
}