package com.felipecosta.kotlinrxjavasample.rx

import android.view.View
import android.view.View.OnClickListener
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

import io.reactivex.android.MainThreadDisposable.verifyMainThread


internal class ViewClickObservable(private val view: View) : Observable<Any>() {

    override fun subscribeActual(observer: Observer<in Any>) {
        verifyMainThread()
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.setOnClickListener(listener)
    }

    internal class Listener(private val view: View, private val observer: Observer<in Any>) : MainThreadDisposable(), OnClickListener {

        override fun onClick(v: View) {
            if (!isDisposed) {
                observer.onNext(Notification.INSTANCE)
            }
        }

        override fun onDispose() {
            view.setOnClickListener(null)
        }
    }

    enum class Notification {
        INSTANCE
    }

}