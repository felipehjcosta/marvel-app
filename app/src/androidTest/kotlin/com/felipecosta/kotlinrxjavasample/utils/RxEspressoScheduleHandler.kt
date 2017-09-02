package com.felipecosta.kotlinrxjavasample.utils

import android.support.test.espresso.IdlingResource
import android.support.test.espresso.idling.CountingIdlingResource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function


class RxEspressoScheduleHandler : Function<Runnable, Runnable> {

    private val countingIdlingResource = CountingIdlingResource("rxJava")

    @Throws(Exception::class)
    override fun apply(@NonNull runnable: Runnable): Runnable {
        return Runnable {
            countingIdlingResource.increment()

            try {
                runnable.run()
            } finally {
                countingIdlingResource.decrement()
            }
        }
    }

    val idlingResource: IdlingResource
        get() = countingIdlingResource

}