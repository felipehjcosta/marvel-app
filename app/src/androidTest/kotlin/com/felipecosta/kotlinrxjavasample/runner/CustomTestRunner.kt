package com.felipecosta.kotlinrxjavasample.runner

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import com.felipecosta.kotlinrxjavasample.MockDemoApplication
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.android.plugins.RxAndroidPlugins


class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MockDemoApplication::class.java.name, context)
    }

    override fun onStart() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(Rx2Idler.create("Main Thread Scheduler"))
        super.onStart()
    }
}