package com.felipecosta.kotlinrxjavasample.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.listing.model.marvel.Character

class DetailActivity : AppCompatActivity() {

    companion object {
        val CHARACTER_OBJECT: String? = "com.felipecosta.kotlinrxjavasample.CHARACTER_BUNDLE"

        fun startDetail(activity: Activity, character: Character?) {
            val bundle = Bundle()
            bundle.putParcelable(CHARACTER_OBJECT, character)

            val activityIntent = Intent(activity, DetailActivity::class.java)
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activityIntent.putExtras(bundle)
            activity.startActivity(activityIntent)
        }
    }

    lateinit var mCharacter: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mCharacter = intent.extras.getParcelable<Character>(CHARACTER_OBJECT)
    }
}
