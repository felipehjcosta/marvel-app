package com.felipecosta.kotlinrxjavasample

import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.DaggerTestApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.TestAppModule
import com.felipecosta.kotlinrxjavasample.di.TestApplicationComponent

class MockDemoApplication : DemoApplication() {

    val applicationComponent: TestApplicationComponent = DaggerTestApplicationComponent.create()


    override fun createComponent(): ApplicationComponent {
        return applicationComponent
    }
}