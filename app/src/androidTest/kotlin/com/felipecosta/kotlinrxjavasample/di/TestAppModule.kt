package com.felipecosta.kotlinrxjavasample.di

import android.content.Context
import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton

@Module
class TestAppModule {

    @Singleton
    @Provides
    fun providesDataRepository(): DataRepository = Mockito.mock(DataRepository::class.java)

    @Singleton
    @Provides
    fun providesApplicationContext(): Context = InstrumentationRegistry.getContext().applicationContext

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context
            .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
}