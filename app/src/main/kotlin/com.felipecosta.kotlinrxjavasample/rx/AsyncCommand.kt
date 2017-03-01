package com.felipecosta.kotlinrxjavasample.rx

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Completable
import io.reactivex.Observable


class AsyncCommand<Result : Any>(private val action: () -> Observable<Result>) : Command {

    private val inputRelay: PublishRelay<Any>

    private val executingRelay: PublishRelay<Boolean>

    private val throwablePublishRelay: PublishRelay<Throwable>

    private val elementsPublishRelay: PublishRelay<Result>

    init {
        this.inputRelay = PublishRelay.create()
        this.executingRelay = PublishRelay.create()

        this.throwablePublishRelay = PublishRelay.create()
        this.elementsPublishRelay = PublishRelay.create()
    }

    override fun execute(): Completable {
        return Observable.defer { action() }
                .doOnSubscribe { executingRelay.accept(true) }
                .doOnNext { elementsPublishRelay.accept(it) }
                .doOnNext { executingRelay.accept(false) }
                .onErrorResumeNext { throwable: Throwable ->
                    executingRelay.accept(false)
                    val emptyObservable = Observable.just(throwable).flatMap { Observable.empty<Result>() }
                    throwablePublishRelay.accept(throwable)
                    emptyObservable
                }
                .ignoreElements()
    }

    val executing: Observable<Boolean>
        get() = executingRelay

    val errors: Observable<Throwable>
        get() = throwablePublishRelay

    val execution: Observable<Result>
        get() = elementsPublishRelay
}