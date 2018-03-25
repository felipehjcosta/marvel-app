package com.github.felipehjcosta.marvelapp.base.di

import com.github.felipehjcosta.marvelapp.base.main.MainActivityTest
import com.github.felipehjcosta.marvelapp.base.modules.listing.di.CharacterListingFragmentBuilderModule
import com.github.felipehjcosta.marvelapp.base.modules.wiki.di.WikiFragmentBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestApplicationModule::class,
    TestNetworkModule::class,
    AppModule::class,
    AndroidInjectionModule::class,
    CharacterListingFragmentBuilderModule::class,
    WikiFragmentBuilderModule::class
])
interface TestApplicationComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)
}