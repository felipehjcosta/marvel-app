package com.github.felipehjcosta.marvelapp.base.launch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.felipehjcosta.marvelapp.base.util.navigateToMain

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMain(this)
        finish()
    }
}
