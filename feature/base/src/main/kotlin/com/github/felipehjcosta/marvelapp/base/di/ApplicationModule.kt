package com.github.felipehjcosta.marvelapp.base.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun providesApplicationContext(application: Application): Context = application.applicationContext
}