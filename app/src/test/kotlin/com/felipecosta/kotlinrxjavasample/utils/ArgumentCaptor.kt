package com.felipecosta.kotlinrxjavasample.utils

import org.mockito.ArgumentCaptor

inline fun <reified T : Any> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)