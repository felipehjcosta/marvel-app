package com.felipecosta.kotlinrxjavasample.di

import kotlin.reflect.KClass

interface HasSubcomponentBuilders {
    fun <A> getSubcomponentBuilder(componentClass: KClass<*>): SubcomponentBuilder<A>
}