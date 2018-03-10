package com.felipecosta.kotlinrxjavasample.launch

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.felipecosta.kotlinrxjavasample.main.MainActivity

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
