package com.github.felipehjcosta.marvelapp.base.cache

import android.content.Context
import com.github.felipehjcosta.marvelapp.cache.CacheDatabase
import com.github.felipehjcosta.marvelapp.cache.newInstance
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {
    @Singleton
    @Provides
    fun providesCacheDatabase(context: Context): CacheDatabase = newInstance(context)
}