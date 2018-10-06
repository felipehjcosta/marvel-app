package com.github.felipehjcosta.marvelapp.base.network

import android.content.Context
import com.github.felipehjcosta.marvelapp.network.NetworkFacade
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

const val PORT = "443"
const val BASE_URL = "https://gateway.marvel.com:$PORT"

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesNetworkFacade(applicationContext: Context): NetworkFacade {
        return NetworkFacade(applicationContext, BASE_URL)
    }

    @Singleton
    @Provides
    fun providesRetrofit(networkFacade: NetworkFacade): Retrofit = networkFacade.retrofit

}