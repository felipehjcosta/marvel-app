package com.felipecosta.kotlinrxjavasample.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.listing.model.marvel.Character
import com.nostra13.universalimageloader.core.ImageLoader

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

    lateinit var character: Character
    lateinit var toolbar: Toolbar
    lateinit var backdrop: ImageView
    lateinit var description: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        character = intent.extras.getParcelable<Character>(CHARACTER_OBJECT)
        initView(character.name)
        loadInfo()
    }

    private fun initView(title: String) {
        setContentView(R.layout.activity_detail)
        toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        backdrop = findViewById(R.id.image_backdrop) as ImageView
        description = findViewById(R.id.text_description) as TextView
    }

    private fun loadInfo() {
        val imageLoader = ImageLoader.getInstance()

        imageLoader.displayImage(character.thumbnail?.path + "." + character.thumbnail?.extension, backdrop)
        description.text = character.description
    }

}
