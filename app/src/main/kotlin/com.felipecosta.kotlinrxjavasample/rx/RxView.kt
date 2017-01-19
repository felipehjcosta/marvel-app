package com.felipecosta.kotlinrxjavasample.rx

import android.support.annotation.CheckResult
import android.support.annotation.RequiresApi
import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Predicate
import java.util.concurrent.Callable


/**
 * Create an observable which emits on `view` click events. The emitted value is
 * unspecified and should only be used as notification.
 *
 * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
 * to free this reference.
 *
 * *Warning:* The created observable uses [View.setOnClickListener] to observe
 * clicks. Only one observable can be used for a view at a time.
 */
inline fun View.clicks(): Observable<Any> = RxView.clicks(this)

/**
 * Static factory methods for creating [observables][Observable] and [ actions][Consumer] for [View].
 */
class RxView private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        /**
         * Create an observable which emits on `view` click events. The emitted value is
         * unspecified and should only be used as notification.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         *
         *
         * *Warning:* The created observable uses [View.setOnClickListener] to observe
         * clicks. Only one observable can be used for a view at a time.
         */
        @CheckResult
        fun clicks(view: View): Observable<Any> {
            checkNotNull(view) { "view == null" }
            return ViewClickObservable(view)
        }

    }
}
