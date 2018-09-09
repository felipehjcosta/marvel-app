package com.github.felipehjcosta.marvelapp.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response

private const val INTERNET_MAX_AGE = 5
private const val NO_INTERNET_MAX_STALE = 60 * 60 * 24 * 7

class OfflineCacheInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = if (hasNetworkAvailable())
            request.newBuilder().header("Cache-Control", "public, max-age=$INTERNET_MAX_AGE").build()
        else
            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=$NO_INTERNET_MAX_STALE").build()
        return chain.proceed(request)
    }

    private fun hasNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}