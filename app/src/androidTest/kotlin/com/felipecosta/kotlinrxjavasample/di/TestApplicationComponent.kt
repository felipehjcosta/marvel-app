package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.main.MainActivityTest
import com.felipecosta.kotlinrxjavasample.modules.listing.di.DetailActivityBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.listing.di.CharacterListingFragmentBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(TestAppModule::class,
        AndroidInjectionModule::class,
        CharacterListingFragmentBuilderModule::class,
        DetailActivityBuilderModule::class
))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)
}