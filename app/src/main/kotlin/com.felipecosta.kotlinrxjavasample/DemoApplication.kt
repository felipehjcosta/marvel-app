package com.felipecosta.kotlinrxjavasample

import android.app.Application
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.DaggerApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.HasSubcomponentBuilders
import com.felipecosta.kotlinrxjavasample.di.SubcomponentBuilder
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
        return DaggerApplicationComponent.create()
    }

    override fun <A> getSubcomponentBuilder(componentClass: KClass<*>): SubcomponentBuilder<A> {
        return subcomponentBuilders[componentClass.java]?.get() as SubcomponentBuilder<A>
    }

}