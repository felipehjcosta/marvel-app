package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.NetworkDataRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @IOScheduler
    @Provides
    fun providesIOScheduler(): Scheduler = Schedulers.newThread()

    @Singleton
    @MainScheduler
    @Provides
    fun providesMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Provides
    fun providesDataRepository(): DataRepository = NetworkDataRepository()
}