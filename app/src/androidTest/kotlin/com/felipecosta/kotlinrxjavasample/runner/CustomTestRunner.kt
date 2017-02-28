package com.felipecosta.kotlinrxjavasample.runner

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.felipecosta.kotlinrxjavasample.MockDemoApplication


class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MockDemoApplication::class.java.name, context)
    }
}