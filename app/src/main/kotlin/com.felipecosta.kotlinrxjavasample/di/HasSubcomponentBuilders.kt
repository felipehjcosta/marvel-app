package com.felipecosta.kotlinrxjavasample.di

import kotlin.reflect.KClass

interface HasSubcomponentBuilders {
    fun <A : SubcomponentBuilder<*>> getSubcomponentBuilder(componentClass: KClass<A>): A
}