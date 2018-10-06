package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.main.MainActivityTest
import dagger.Component

@UITestScope
@Component(modules = [TestNetworkModule::class],
        dependencies = [ApplicationComponent::class]
)
interface TestApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)

    @Component.Builder
    interface Builder {
        fun testNetworkModule(testNetworkModule: TestNetworkModule): Builder

        fun plus(component: ApplicationComponent): Builder

        fun build(): TestApplicationComponent
    }

}