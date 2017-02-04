package com.felipecosta.kotlinrxjavasample.rx

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable


class AsyncCommand<Result : Any>(private val action: () -> Observable<Result>) : Command {

    private val inputRelay: BehaviorRelay<Any>

    private val executingRelay: BehaviorRelay<Boolean>

    private val throwablePublishRelay: PublishRelay<Throwable>

    private val elementsPublishRelay: PublishRelay<Result>

    private val inputObservable: Observable<Result>

    init {
        this.inputRelay = BehaviorRelay.create()
        this.executingRelay = BehaviorRelay.create()

        this.throwablePublishRelay = PublishRelay.create()
        this.elementsPublishRelay = PublishRelay.create()

        inputObservable = inputRelay
                .doOnNext { executingRelay.accept(true) }
                .flatMap { action() }
                .doOnNext { elementsPublishRelay.accept(it) }
                .doOnNext { executingRelay.accept(false) }
                .onErrorResumeNext { throwable: Throwable ->
                    executingRelay.accept(false)
                    val emptyObservable = Observable.just(throwable).flatMap { Observable.empty<Result>() }
                    throwablePublishRelay.accept(throwable)
                    emptyObservable
                }
                .share()
    }

    override fun execute(): Observable<out Any> {
        inputRelay.accept(Any())

        return inputObservable
    }

    val executing: Observable<Boolean>
        get() = executingRelay

    val errors: Observable<Throwable>
        get() = throwablePublishRelay

    val execution: Observable<Result>
        get() = elementsPublishRelay
}