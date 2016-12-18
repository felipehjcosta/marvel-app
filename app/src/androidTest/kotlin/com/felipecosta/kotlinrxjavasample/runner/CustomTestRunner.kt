package com.felipecosta.kotlinrxjavasample.runner

import android.support.test.runner.AndroidJUnitRunner
import android.support.test.InstrumentationRegistry.getTargetContext
import android.support.test.InstrumentationRegistry
import android.os.Bundle
import com.linkedin.android.testbutler.TestButler

class CustomTestRunner :AndroidJUnitRunner(){

    override fun onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext())
        super.onStart()
    }

    override fun finish(resultCode: Int, results: Bundle) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext())
        super.finish(resultCode, results)
    }
}