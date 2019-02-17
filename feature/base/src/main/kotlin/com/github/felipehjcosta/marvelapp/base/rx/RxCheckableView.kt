package com.github.felipehjcosta.marvelapp.base.rx

import androidx.annotation.CheckResult
import com.example.checkableheart.ui.HeartFab
import com.jakewharton.rxbinding3.InitialValueObservable


fun HeartFab.checkedChanges(): InitialValueObservable<Boolean> =
    RxCheckableView.checkedChanges(this)

class RxCheckableView private constructor() {

    companion object {

        @CheckResult
        fun checkedChanges(view: HeartFab): InitialValueObservable<Boolean> {
            checkNotNull(view)
            return CheckableViewCheckedChangeObservable(view)
        }
    }
}
