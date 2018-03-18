package com.github.felipehjcosta.appdetail.view

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.checkableheart.ui.HeartFab
import com.felipecosta.kotlinrxjavasample.rx.checkedChanges
import com.felipecosta.kotlinrxjavasample.rx.plusAssign
import com.felipecosta.kotlinrxjavasample.util.bindView
import com.github.felipehjcosta.appdetail.R
import com.github.felipehjcosta.appdetail.presentation.CharacterDetailViewModel
import com.nostra13.universalimageloader.core.ImageLoader
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: CharacterDetailViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    private val toolbarLayout: CollapsingToolbarLayout by bindView(R.id.toolbar_layout)
    private val backdrop: ImageView by bindView(R.id.image_backdrop)
    private val description: TextView by bindView(R.id.text_description)
    private val statisticComics: TextView by bindView(R.id.statistic_comics)
    private val statisticEvents: TextView by bindView(R.id.statistic_events)
    private val statisticSeries: TextView by bindView(R.id.statistic_series)
    private val statisticStories: TextView by bindView(R.id.statistic_stories)
    private val favoriteFab: HeartFab by bindView(R.id.favorite_button)
    private lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_detail)
        val toolbar: Toolbar by bindView(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        imageLoader = ImageLoader.getInstance()
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable += viewModel.name.subscribe { toolbarLayout.title = it }
        compositeDisposable += viewModel.description.subscribe { description.text = it }
        compositeDisposable += viewModel.thumbnailUrl.subscribe { imageLoader.displayImage(it, backdrop) }

        compositeDisposable += viewModel.comicsCount.subscribe { statisticComics.text = it.toString() }
        compositeDisposable += viewModel.eventsCount.subscribe { statisticEvents.text = it.toString() }
        compositeDisposable += viewModel.seriesCount.subscribe { statisticSeries.text = it.toString() }
        compositeDisposable += viewModel.storiesCount.subscribe { statisticStories.text = it.toString() }
        compositeDisposable += viewModel.isFavorite.subscribe { favoriteFab.isChecked = it }

        compositeDisposable += viewModel.characterCommand.execute().subscribe()

        compositeDisposable += favoriteFab.checkedChanges()
                .subscribe { if (it) viewModel.saveFavorite() else viewModel.removeFavorite() }
    }

    override fun onPause() {
        super.onPause()
        unbind()
    }

    private fun unbind() {
        compositeDisposable.dispose()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}
