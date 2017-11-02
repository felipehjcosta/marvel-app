package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.main.MainActivityTest
import com.felipecosta.kotlinrxjavasample.modules.listing.di.CharacterListingFragmentBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.detail.di.DetailActivityBuilderModule
import com.felipecosta.kotlinrxjavasample.modules.wiki.di.WikiFragmentBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        TestApplicationModule::class,
        TestNetworkModule::class,
        AppModule::class,
        AndroidInjectionModule::class,
        CharacterListingFragmentBuilderModule::class,
        DetailActivityBuilderModule::class,
        WikiFragmentBuilderModule::class
))
interface TestApplicationComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)
}