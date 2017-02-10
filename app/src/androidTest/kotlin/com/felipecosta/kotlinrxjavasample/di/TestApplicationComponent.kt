package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.di.SubcomponentBuilderBindersModule
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.TestAppModule
import com.felipecosta.kotlinrxjavasample.main.MainActivityTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestAppModule::class, SubcomponentBuilderBindersModule::class))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)
}