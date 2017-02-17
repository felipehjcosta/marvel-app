package com.felipecosta.kotlinrxjavasample.rx

import android.support.annotation.CheckResult
import com.example.checkableheart.ui.HeartFab
import com.jakewharton.rxbinding2.InitialValueObservable


inline fun HeartFab.checkedChanges(): InitialValueObservable<Boolean> = RxCheckableView.checkedChanges(this)

class RxCheckableView private constructor() {


    init {
    }

    companion object {

        @CheckResult
        fun checkedChanges(view: HeartFab): InitialValueObservable<Boolean> {
            checkNotNull(view)
            return CheckableViewCheckedChangeObservable(view)
        }
    }
}