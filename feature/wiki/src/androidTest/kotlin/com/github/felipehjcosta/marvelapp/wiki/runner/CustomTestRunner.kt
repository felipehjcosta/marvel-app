package com.github.felipehjcosta.marvelapp.wiki.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnitRunner
import com.github.felipehjcosta.marvelapp.wiki.MockMarvelAppApplication
import com.linkedin.android.testbutler.TestButler
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins


class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MockMarvelAppApplication::class.java.name, context)
    }

    override fun onStart() {
        TestButler.setup(ApplicationProvider.getApplicationContext())
        RxJavaPlugins.setInitNewThreadSchedulerHandler(Rx2Idler.create("New Thread Scheduler"))
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(ApplicationProvider.getApplicationContext())
        super.finish(resultCode, results)
    }
}