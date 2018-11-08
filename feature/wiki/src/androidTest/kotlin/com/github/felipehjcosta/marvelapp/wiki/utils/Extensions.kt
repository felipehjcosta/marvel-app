package com.github.felipehjcosta.marvelapp.wiki.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider

fun readAsset(fileName: String) = ApplicationProvider.getApplicationContext<Context>()
        .assets
        .open(fileName)
        .bufferedReader()
        .use { it.readText() }