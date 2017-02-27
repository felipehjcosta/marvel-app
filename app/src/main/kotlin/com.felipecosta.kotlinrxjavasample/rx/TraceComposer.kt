package com.felipecosta.kotlinrxjavasample.rx

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

class TraceComposer<T> constructor(private val prefix: String) : ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>?): ObservableSource<T> {
        return upstream!!
                .doOnSubscribe { disposable -> println("[$prefix] doOnSubscribe") }
                .doOnDispose { println("[$prefix] doOnDispose") }
                .doOnNext { value -> println("[$prefix] doOnNext $value") }
                .doOnError { error -> println("[$prefix] doOnError $error") }
                .doOnComplete { println("[$prefix] doOnComplete") }
                .doOnTerminate { println("[$prefix] doOnTerminate") }
                .doAfterTerminate { println("[$prefix] doAfterTerminate") }
                .doFinally { println("[$prefix] doFinally") }
    }

    companion object {
        fun <T> apply(prefix: String) = TraceComposer<T>(prefix)
    }
}
