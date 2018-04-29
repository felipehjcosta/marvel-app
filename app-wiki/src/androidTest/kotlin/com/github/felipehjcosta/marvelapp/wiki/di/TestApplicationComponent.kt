package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.base.di.AppModule
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.main.MainActivityTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    TestApplicationModule::class,
    TestNetworkModule::class,
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class
])
interface TestApplicationComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)
}