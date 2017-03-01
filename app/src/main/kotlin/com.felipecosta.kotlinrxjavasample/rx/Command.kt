package com.felipecosta.kotlinrxjavasample.rx

import io.reactivex.Completable

interface Command {
    fun execute(): Completable
}