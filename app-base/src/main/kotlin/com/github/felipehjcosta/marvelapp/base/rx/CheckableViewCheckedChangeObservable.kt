package com.github.felipehjcosta.marvelapp.base.rx

import com.example.checkableheart.ui.HeartFab
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


class CheckableViewCheckedChangeObservable(private val view: HeartFab) : InitialValueObservable<Boolean>() {

    override fun subscribeListener(observer: Observer<in Boolean>) {
        if (!checkMainThread(observer)) {
            return
        }
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnCheckedChangeListener(listener)
    }

    override fun getInitialValue(): Boolean {
        return view.isChecked
    }

    internal class Listener(private val view: HeartFab, private val observer: Observer<in Boolean>) : MainThreadDisposable(), HeartFab.OnCheckedChangeListener {

        override fun onCheckedChanged(view: HeartFab, isChecked: Boolean) {
            if (!isDisposed) {
                observer.onNext(isChecked)
            }
        }

        override fun onDispose() {
            view.setOnCheckedChangeListener(null)
        }
    }
}
