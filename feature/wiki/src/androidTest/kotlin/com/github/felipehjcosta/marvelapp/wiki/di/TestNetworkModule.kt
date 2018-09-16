package com.github.felipehjcosta.marvelapp.wiki.di

import com.github.felipehjcosta.marvelapp.base.BuildConfig
import com.github.felipehjcosta.marvelapp.base.data.CharacterService
import com.github.felipehjcosta.marvelapp.base.util.createNetworkConverterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
class TestNetworkModule {

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }

    }.build()

    @Provides
    @Singleton
    fun providesMockWebServer(): MockWebServer = MockWebServer().apply { start() }

    @Singleton
    @Provides
    fun providesCharacterService(httpClient: OkHttpClient, mockWebServer: MockWebServer): CharacterService = Retrofit.Builder()
            .baseUrl("http://${mockWebServer.hostName}:${mockWebServer.port}")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(createNetworkConverterFactory())
            .client(httpClient)
            .build().create(CharacterService::class.java)
}