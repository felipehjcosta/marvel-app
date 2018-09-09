package com.github.felipehjcosta.marvelapp.base.di

import android.content.Context
import com.github.felipehjcosta.marvelapp.base.BuildConfig
import com.github.felipehjcosta.marvelapp.base.data.CharacterService
import com.github.felipehjcosta.marvelapp.base.network.OfflineCacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val PORT = "443"
const val BASE_URL = "https://gateway.marvel.com:$PORT"
const val NETWORK_CACHE_SIZE = (5 * 1024 * 1024).toLong()

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(applicationContext: Context): OkHttpClient {
        val cache = Cache(applicationContext.cacheDir, NETWORK_CACHE_SIZE)
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(OfflineCacheInterceptor(applicationContext))
                .apply {
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                        addInterceptor(logging)
                    }
                }.build()
    }

    @Singleton
    @Provides
    fun providesCharacterService(httpClient: OkHttpClient): CharacterService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build().create(CharacterService::class.java)
}