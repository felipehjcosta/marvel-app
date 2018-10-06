package com.github.felipehjcosta.marvelapp.wiki

import com.github.felipehjcosta.marvelapp.base.DemoApplication
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.di.DaggerApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.di.DaggerTestApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.di.TestApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.di.TestNetworkModule

class MockDemoApplication : DemoApplication() {

    lateinit var testApplicationComponent: TestApplicationComponent

    override fun createComponent(): ApplicationComponent {
        val applicationComponent = DaggerApplicationComponent.builder()
                .application(this)
                .baseUrl("http://localhost:8080/")
                .build()

        testApplicationComponent = DaggerTestApplicationComponent.builder()
                .plus(applicationComponent)
                .testNetworkModule(TestNetworkModule(8080))
                .build()

        return applicationComponent
    }
}