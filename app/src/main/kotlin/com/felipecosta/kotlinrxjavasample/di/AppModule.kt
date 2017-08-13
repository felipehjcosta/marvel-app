package com.felipecosta.kotlinrxjavasample.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.CacheDataRepository
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.NetworkDataRepository
import com.felipecosta.kotlinrxjavasample.data.SimpleDiskCache
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesDiskCache(application: Application): SimpleDiskCache {
        val fileCache = File(application.cacheDir, "disk_cache")
        return SimpleDiskCache.open(fileCache, 1, Integer.MAX_VALUE.toLong())
    }

    @Singleton
    @Provides
    fun providesDataRepository(cache: SimpleDiskCache): DataRepository {
        return CacheDataRepository(NetworkDataRepository(), cache)
    }

    @Singleton
    @Provides
    fun providesApplicationContext(application: Application): Context = application

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context
            .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

}