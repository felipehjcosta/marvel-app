package com.felipecosta.kotlinrxjavasample.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestAppModule {

    @Singleton
    @Provides
    fun providesDataRepository(): DataRepository = Mockito.mock(DataRepository::class.java)
}