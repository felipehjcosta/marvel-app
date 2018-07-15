package com.github.felipehjcosta.marvelapp.detail

import android.app.Application

class TestStubApplication : Application() {
    override fun onCreate() {
        setTheme(android.support.v7.appcompat.R.style.Theme_AppCompat_NoActionBar)
        super.onCreate()
    }
}