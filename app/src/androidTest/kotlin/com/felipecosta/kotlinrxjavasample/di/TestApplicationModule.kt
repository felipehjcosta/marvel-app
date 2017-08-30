package com.felipecosta.kotlinrxjavasample.di

import android.content.Context
import android.support.test.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule {

    @Singleton
    @Provides
    fun providesApplicationContext(): Context = InstrumentationRegistry.getContext().applicationContext
}