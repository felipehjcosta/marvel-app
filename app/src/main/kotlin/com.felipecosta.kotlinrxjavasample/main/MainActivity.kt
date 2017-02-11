package com.felipecosta.kotlinrxjavasample.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.view.ListingFragment
import com.felipecosta.kotlinrxjavasample.samples.Sample2Fragment
import com.felipecosta.kotlinrxjavasample.samples.Sample3Fragment
import com.jakewharton.rxbinding2.support.design.widget.itemSelections
import io.reactivex.Observable.merge
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setupViews()
        bind()
    }

    private fun setupViews() {
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.nav_view) as BottomNavigationView
    }

    private fun bind() {

        val itemSelectionObservable = bottomNavigationView.itemSelections().share()

        val sample1FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_1 }.
                map { ListingFragment.newInstance() }

        val sample2FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_2 }.
                map { Sample2Fragment.newInstance() }

        val sample3FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_3 }.
                map { Sample3Fragment.newInstance() }

        val contentFragmentObservable = merge(listOf(sample1FragmentObservable,
                sample2FragmentObservable,
                sample3FragmentObservable))

        disposable = contentFragmentObservable.
                filter { !(supportFragmentManager.findFragmentById(R.id.main_content)?.javaClass?.equals(it.javaClass) ?: false) }.
                subscribe { supportFragmentManager.beginTransaction().replace(R.id.main_content, it).commit() }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
    }

    private fun unbind() {
        disposable.dispose()
    }
}
