package com.github.felipehjcosta.marvelapp.wiki

import com.github.felipehjcosta.marvelapp.base.DemoApplication
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.di.DaggerTestApplicationComponent
import com.github.felipehjcosta.marvelapp.wiki.di.TestApplicationComponent

class MockDemoApplication : DemoApplication() {

    val applicationComponent: TestApplicationComponent = DaggerTestApplicationComponent.create()

    override fun createComponent(): ApplicationComponent {
        return applicationComponent
    }
}