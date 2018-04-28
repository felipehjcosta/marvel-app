package com.github.felipehjcosta.marvelapp.wiki.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.github.felipehjcosta.marvelapp.base.modules.favorite.view.FavoriteFragment
import com.github.felipehjcosta.marvelapp.base.util.bindView
import com.github.felipehjcosta.marvelapp.wiki.R
import com.jakewharton.rxbinding2.support.design.widget.itemSelections
import io.reactivex.Observable.merge
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    private val bottomNavigationView: BottomNavigationView by bindView(R.id.nav_view)

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        bind()
    }

    private fun bind() {

        val itemSelectionObservable = bottomNavigationView.itemSelections().share()

        val sample1FragmentObservable = itemSelectionObservable.filter { it.itemId == R.id.nav_listing }.map { WikiFragment.newInstance() }

        val sample2FragmentObservable = itemSelectionObservable.filter { it.itemId == R.id.nav_favorite }.map { FavoriteFragment.newInstance() }

        val contentFragmentObservable = merge(listOf(sample1FragmentObservable,
                sample2FragmentObservable))

        disposable = contentFragmentObservable.filter {
            !(supportFragmentManager.findFragmentById(R.id.main_content)?.javaClass?.equals(it.javaClass)
                    ?: false)
        }.subscribe { supportFragmentManager.beginTransaction().replace(R.id.main_content, it).commit() }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
    }

    private fun unbind() {
        disposable.dispose()
    }
}
