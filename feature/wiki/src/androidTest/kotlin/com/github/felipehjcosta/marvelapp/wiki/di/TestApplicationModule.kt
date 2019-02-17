package com.github.felipehjcosta.marvelapp.wiki.di

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule {

    @Singleton
    @Provides
    fun providesApplicationContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext
}