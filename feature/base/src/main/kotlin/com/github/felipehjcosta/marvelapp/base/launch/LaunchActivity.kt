package com.github.felipehjcosta.marvelapp.base.launch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.felipehjcosta.marvelapp.base.navigator.UrlBasedAppNavigator

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UrlBasedAppNavigator().showWiki(this)
        finish()
    }
}
