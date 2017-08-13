package com.felipecosta.kotlinrxjavasample.di

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Qualifier

@Qualifier
@Documented
@Retention(RUNTIME)
annotation class IOScheduler(val name: String = "")