package com.github.felipehjcosta.marvelapp.network.di

import android.content.Context
import com.github.felipehjcosta.marvelapp.network.BuildConfig
import com.github.felipehjcosta.marvelapp.network.interceptor.OfflineCacheInterceptor
import com.github.felipehjcosta.marvelapp.network.utils.createNetworkConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
    fun providesRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(createNetworkConverterFactory())
            .client(httpClient)
            .build()

}