package com.github.felipehjcosta.marvelapp.base.di

import android.content.Context
import android.content.SharedPreferences
import com.github.felipehjcosta.marvelapp.base.R
import com.github.felipehjcosta.marvelapp.base.character.data.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.io.File
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesDiskCache(context: Context): SimpleDiskCache {
        val fileCache = File(context.cacheDir, "disk_cache")
        return SimpleDiskCache.open(fileCache, 1, Integer.MAX_VALUE.toLong())
    }

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context
            .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

}