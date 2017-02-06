package com.felipecosta.kotlinrxjavasample.di

interface SubcomponentBuilder<A> {
    fun build(): A
}