package com.github.felipehjcosta.marvelapp.test

import android.app.Application

class TestStubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_NoActionBar)
    }
}
