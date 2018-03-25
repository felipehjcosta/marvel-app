package com.github.felipehjcosta.marvelapp.base.di

import com.github.felipehjcosta.marvelapp.base.BuildConfig
import com.github.felipehjcosta.marvelapp.base.data.CharacterService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val PORT = "80"
const val BASE_URL = "http://gateway.marvel.com" + ":" + PORT

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
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
}