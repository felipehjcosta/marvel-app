package com.github.felipehjcosta.marvelapp.network.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Converter

fun createNetworkConverterFactory(
        contentType: MediaType = MediaType.parse("application/json")!!
): Converter.Factory {
    val json = Json {
        ignoreUnknownKeys = true
    }
    return json.asConverterFactory(contentType)
}
