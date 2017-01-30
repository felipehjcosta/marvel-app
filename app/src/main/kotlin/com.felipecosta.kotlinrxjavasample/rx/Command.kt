package com.felipecosta.kotlinrxjavasample.rx

import io.reactivex.Observable

interface Command {
    fun execute(): Observable<out Any>
}