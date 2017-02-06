package com.felipecosta.kotlinrxjavasample.di

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
annotation class SubcomponentKey(val value: KClass<*>)