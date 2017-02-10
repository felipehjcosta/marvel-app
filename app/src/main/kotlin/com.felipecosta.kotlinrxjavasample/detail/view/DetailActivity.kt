package com.felipecosta.kotlinrxjavasample.detail.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.detail.datamodel.DetailDataModel
import com.felipecosta.kotlinrxjavasample.detail.di.DetailComponent
import com.felipecosta.kotlinrxjavasample.detail.presentation.CharacterDetailViewModel
import com.felipecosta.kotlinrxjavasample.di.HasSubcomponentBuilders
import com.nostra13.universalimageloader.core.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    companion object {
        fun startDetail(activity: Activity) {
            val activityIntent = Intent(activity, DetailActivity::class.java)
            activityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(activityIntent)
        }
    }

    @Inject
    lateinit var dataModel: DetailDataModel
    @Inject
    lateinit var viewModel: CharacterDetailViewModel
    lateinit var toolbarLayout: CollapsingToolbarLayout
    lateinit var backdrop: ImageView
    lateinit var description: TextView
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val application = application
        if (application is HasSubcomponentBuilders) {
            val subComponent = application.getSubcomponentBuilder<DetailComponent>(DetailComponent::class).build()
            subComponent.inject(this)
        }
        initView()
    }

    override fun onResume() {
        super.onResume()
        dataModel.character(1009718)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { character -> loadInfo(character) }
    }

    private fun initView() {
        setContentView(R.layout.activity_detail)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarLayout = findViewById(R.id.toolbar_layout) as CollapsingToolbarLayout

        backdrop = findViewById(R.id.image_backdrop) as ImageView
        description = findViewById(R.id.text_description) as TextView

        imageLoader = ImageLoader.getInstance()
    }

    private fun loadInfo(character: Character) {
        toolbarLayout.title = character.name
        imageLoader.displayImage(character.thumbnail?.path + "." + character.thumbnail?.extension, backdrop)
        description.text = character.description
    }

}
