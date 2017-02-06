package com.felipecosta.kotlinrxjavasample.di


import com.felipecosta.kotlinrxjavasample.DemoApplication
import dagger.Component
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApplicationBinders::class))
interface ApplicationComponent {
    fun inject(demoApplication: DemoApplication)

    fun subcomponentBuidlers(): Map<Class<*>, Provider<SubcomponentBuilder<*>>>
}