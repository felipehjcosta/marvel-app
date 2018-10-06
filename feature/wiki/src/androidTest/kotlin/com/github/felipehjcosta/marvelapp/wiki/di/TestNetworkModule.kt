package com.github.felipehjcosta.marvelapp.wiki.di

import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer

@Module
class TestNetworkModule(private val mockWebServerPort: Int) {

    @Provides
    @UITestScope
    fun providesMockWebServer(): MockWebServer = MockWebServer().apply { start(mockWebServerPort) }
}