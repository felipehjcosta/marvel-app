package com.felipecosta.kotlinrxjavasample.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnitRunner
import com.felipecosta.kotlinrxjavasample.MockDemoApplication
import com.linkedin.android.testbutler.TestButler
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.android.plugins.RxAndroidPlugins


class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MockDemoApplication::class.java.name, context)
    }

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(Rx2Idler.create("Main Thread Scheduler"))
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }
}