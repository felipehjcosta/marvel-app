package com.github.felipehjcosta.marvelapp.detail.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.rx.plusAssign
import com.github.felipehjcosta.marvelapp.base.view.viewBinding
import com.github.felipehjcosta.marvelapp.detail.R
import com.github.felipehjcosta.marvelapp.detail.databinding.ActivityDetailBinding
import com.github.felipehjcosta.marvelapp.detail.di.setupDependencyInjection
import com.github.felipehjcosta.marvelapp.detail.presentation.CharacterDetailViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityDetailBinding::inflate)

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
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        bind()
    }

    private fun bind() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable += viewModel.output.name.subscribe { binding.toolbarLayout.title = it }
        compositeDisposable += viewModel.output.description.subscribe { binding.contentDetails.description.text = it }
        compositeDisposable += viewModel.output.thumbnailUrl.subscribe {
            imageLoader.loadImage(
                    it,
                    binding.imageBackdrop
            )
        }

        compositeDisposable += viewModel.output.comicsCount.subscribe {
            binding.detailCharacterStatistic.statisticComics.text = it.toString()
        }
        compositeDisposable += viewModel.output.eventsCount.subscribe {
            binding.detailCharacterStatistic.statisticEvents.text = it.toString()
        }
        compositeDisposable += viewModel.output.seriesCount.subscribe {
            binding.detailCharacterStatistic.statisticSeries.text = it.toString()
        }
        compositeDisposable += viewModel.output.storiesCount.subscribe {
            binding.detailCharacterStatistic.statisticStories.text = it.toString()
        }

        compositeDisposable += viewModel.input.characterCommand.execute().subscribe()
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
