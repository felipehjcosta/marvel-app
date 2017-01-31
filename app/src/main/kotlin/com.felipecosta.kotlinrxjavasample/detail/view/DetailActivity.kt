package com.felipecosta.kotlinrxjavasample.detail.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.data.pojo.Thumbnail
import com.nostra13.universalimageloader.core.ImageLoader
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        val CHARACTER_OBJECT: String? = "com.felipecosta.kotlinrxjavasample.CHARACTER_BUNDLE"

        fun startDetail(activity: Activity) {
            val bundle = Bundle()
            bundle.putParcelable(CHARACTER_OBJECT, getCharacter())

            val activityIntent = Intent(activity, DetailActivity::class.java)
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activityIntent.putExtras(bundle)
            activity.startActivity(activityIntent)
        }

        private fun getCharacter(): Character {
            val char = Character()
            char.id = 1009718
            char.name = "Wolverine"
            char.description = "Born with super-human senses and the power to heal from almost any wound, Wolverine was captured by a secret Canadian organization and given an unbreakable skeleton and claws. Treated like an animal, it took years for him to control himself. Now, he's a premiere member of both the X-Men and the Avengers."
            char.modified = Date() // "2014-06-10T16:13:25-0400",
            val thumbnail = Thumbnail()
            thumbnail.path = "http://i.annihil.us/u/prod/marvel/i/mg/2/60/537bcaef0f6cf"
            thumbnail.extension = "jpg"
            char.thumbnail = thumbnail
            char.resourceURI = "http://gateway.marvel.com/v1/public/characters/1009718"
            return char
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
