package com.felipecosta.kotlinrxjavasample.utils

import org.mockito.Mockito
import org.mockito.stubbing.Answer
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)!!

inline fun <reified T : Any> mock(defaultAnswer: Answer<*>): T = Mockito.mock(T::class.java, defaultAnswer)!!

fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!