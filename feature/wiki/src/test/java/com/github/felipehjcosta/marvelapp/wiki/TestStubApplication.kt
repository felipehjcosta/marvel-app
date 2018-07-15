package com.github.felipehjcosta.marvelapp.wiki

import android.app.Application

class TestStubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar)
    }
}