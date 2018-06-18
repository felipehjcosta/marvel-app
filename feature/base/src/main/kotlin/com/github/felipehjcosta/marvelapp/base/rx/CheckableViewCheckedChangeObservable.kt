package com.github.felipehjcosta.marvelapp.base.rx

import android.os.Looper
import com.example.checkableheart.ui.HeartFab
import com.jakewharton.rxbinding2.InitialValueObservable
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

    private fun checkMainThread(observer: Observer<*>): Boolean {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer.onError(IllegalStateException(
                    "Expected to be called on the main thread but was " + Thread.currentThread().name))
            return false
        }
        return true
    }
}
