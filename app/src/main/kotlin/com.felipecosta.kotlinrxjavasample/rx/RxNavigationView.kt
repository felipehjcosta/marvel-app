package com.felipecosta.kotlinrxjavasample.rx

import android.support.annotation.CheckResult
import android.support.design.widget.NavigationView
import android.view.MenuItem
import io.reactivex.Observable

inline fun NavigationView.itemSelections(): Observable<MenuItem> = RxNavigationView.itemSelections(this)

class RxNavigationView private constructor() {

    init {
        throw AssertionError("No instances.")
    }

    companion object {
        /**
         * Create an observable which emits the selected item in `view`.
         *
         *
         * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
         * to free this reference.
         *
         *
         * *Note:* If an item is already selected, it will be emitted immediately on subscribe.
         * This behavior assumes but does not enforce that the items are exclusively checkable.
         */
        @CheckResult
        fun itemSelections(view: NavigationView): Observable<MenuItem> {
            checkNotNull(view) { "view == null" }
            return Observable.create(NavigationViewItemSelectionsOnSubscribe(view))
        }
    }
}