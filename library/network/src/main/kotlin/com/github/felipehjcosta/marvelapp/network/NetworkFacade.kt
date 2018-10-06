package com.github.felipehjcosta.marvelapp.network

import android.content.Context
import com.github.felipehjcosta.marvelapp.network.interceptor.OfflineCacheInterceptor
import com.github.felipehjcosta.marvelapp.network.utils.createNetworkConverterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

const val NETWORK_CACHE_SIZE = (5 * 1024 * 1024).toLong()

class NetworkFacade(
        applicationContext: Context,
        baseUrl: String,
        networkCacheSize: Long = NETWORK_CACHE_SIZE
) {

    val httpClient: OkHttpClient

    val retrofit: Retrofit

    init {
        val cache = Cache(applicationContext.applicationContext.cacheDir, networkCacheSize)
        httpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(OfflineCacheInterceptor(applicationContext.applicationContext))
                .apply {
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                        addInterceptor(logging)
                    }
                }.build()

        retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(createNetworkConverterFactory())
                .client(httpClient)
                .build()
    }
}

