package com.felipecosta.kotlinrxjavasample.di

import android.app.Application
import com.felipecosta.kotlinrxjavasample.data.CacheDataRepository
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.NetworkDataRepository
import com.felipecosta.kotlinrxjavasample.data.SimpleDiskCache
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun providesDiskCache(): SimpleDiskCache {
        val fileCache = File(application.filesDir, "disk_cache")
        return SimpleDiskCache.open(fileCache, 1, Integer.MAX_VALUE.toLong())
    }

    @Singleton
    @Provides
    fun providesDataRepository(cache: SimpleDiskCache): DataRepository {
        return CacheDataRepository(NetworkDataRepository(), cache)
    }
}