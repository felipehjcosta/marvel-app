package com.github.felipehjcosta.marvelapp.base

import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.di.TestApplicationComponent
import com.github.felipehjcosta.marvelapp.base.di.DaggerTestApplicationComponent

class MockDemoApplication : DemoApplication() {

    val applicationComponent: TestApplicationComponent = DaggerTestApplicationComponent.create()

    override fun createComponent(): ApplicationComponent {
        return applicationComponent
    }
}