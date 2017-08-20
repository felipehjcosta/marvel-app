package com.felipecosta.kotlinrxjavasample.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.felipecosta.kotlinrxjavasample.BuildConfig
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

const val PORT = "80"
const val BASE_URL = "http://gateway.marvel.com" + ":" + PORT

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
    fun providesHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
    }.build()

    @Singleton
    @Provides
    fun providesCharacterService(httpClient: OkHttpClient): CharacterService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build().create(CharacterService::class.java)

    @Singleton
    @Provides
    fun providesDataRepository(characterService: CharacterService, cache: SimpleDiskCache): DataRepository {
        return CacheDataRepository(NetworkDataRepository(characterService), cache)
    }

    @Singleton
    @Provides
    fun providesApplicationContext(application: Application): Context = application

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context
            .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

}