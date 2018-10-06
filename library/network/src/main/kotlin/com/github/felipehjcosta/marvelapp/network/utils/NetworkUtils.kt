package com.github.felipehjcosta.marvelapp.network.utils

import com.jakewharton.retrofit2.converter.kotlinx.serialization.stringBased
import kotlinx.serialization.json.JSON
import okhttp3.MediaType
import retrofit2.Converter

fun createNetworkConverterFactory(
        contentType: MediaType = MediaType.parse("application/json")!!,
        json: JSON = JSON.nonstrict
): Converter.Factory {
    return stringBased(contentType, json::parse, json::stringify)
}