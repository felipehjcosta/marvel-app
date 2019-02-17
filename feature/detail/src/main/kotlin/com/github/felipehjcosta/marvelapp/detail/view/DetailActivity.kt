package com.github.felipehjcosta.marvelapp.detail.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.rx.plusAssign
import com.github.felipehjcosta.marvelapp.detail.R
import com.github.felipehjcosta.marvelapp.detail.di.setupDependencyInjection
import com.github.felipehjcosta.marvelapp.detail.presentation.CharacterDetailViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.toolbar
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_detail.favorite_button as favoriteFab
import kotlinx.android.synthetic.main.activity_detail.image_backdrop as backdrop
import kotlinx.android.synthetic.main.activity_detail.toolbar_layout as toolbarLayout
import kotlinx.android.synthetic.main.content_details.text_description as description
import kotlinx.android.synthetic.main.detail_character_statistic.statistic_comics as statisticComics
import kotlinx.android.synthetic.main.detail_character_statistic.statistic_events as statisticEvents
import kotlinx.android.synthetic.main.detail_character_statistic.statistic_series as statisticSeries
import kotlinx.android.synthetic.main.detail_character_statistic.statistic_stories as statisticStories


class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: CharacterDetailViewModel

    private lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        setupDependencyInjection()
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable += viewModel.name.subscribe { toolbarLayout.title = it }
        compositeDisposable += viewModel.description.subscribe { description.text = it }
        compositeDisposable += viewModel.thumbnailUrl.subscribe {
            imageLoader.loadImage(
                it,
                backdrop
            )
        }

        compositeDisposable += viewModel.comicsCount.subscribe {
            statisticComics.text = it.toString()
        }
        compositeDisposable += viewModel.eventsCount.subscribe {
            statisticEvents.text = it.toString()
        }
        compositeDisposable += viewModel.seriesCount.subscribe {
            statisticSeries.text = it.toString()
        }
        compositeDisposable += viewModel.storiesCount.subscribe {
            statisticStories.text = it.toString()
        }

        compositeDisposable += viewModel.characterCommand.execute().subscribe()
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
