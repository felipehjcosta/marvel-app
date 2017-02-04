package com.felipecosta.kotlinrxjavasample.main

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.modules.listing.view.ListingFragment
import com.felipecosta.kotlinrxjavasample.rx.clicks
import com.felipecosta.kotlinrxjavasample.rx.itemSelections
import com.felipecosta.kotlinrxjavasample.samples.Sample2Fragment
import com.felipecosta.kotlinrxjavasample.samples.Sample3Fragment
import com.felipecosta.kotlinrxjavasample.samples.Sample4Fragment
import io.reactivex.Observable.merge
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var navigationView: NavigationView

    lateinit var fab: FloatingActionButton

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

        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view) as NavigationView

        fab = findViewById(R.id.fab) as FloatingActionButton
    }

    private fun bind() {

        val itemSelectionObservable = navigationView.itemSelections().share()

        val sample1FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_1 }.
                map { ListingFragment.newInstance() }

        val sample2FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_2 }.
                map { Sample2Fragment.newInstance() }

        val sample3FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_3 }.
                map { Sample3Fragment.newInstance() }

        val sample4FragmentObservable = itemSelectionObservable.
                filter { it.itemId == R.id.nav_sample_4 }.
                map { Sample4Fragment.newInstance() }

        val contentFragmentObservable = merge(listOf(sample1FragmentObservable,
                sample2FragmentObservable,
                sample3FragmentObservable,
                sample4FragmentObservable))

        disposable = contentFragmentObservable.
                doOnNext { drawer.closeDrawer(GravityCompat.START) }.
                filter { !(supportFragmentManager.findFragmentById(R.id.main_content)?.javaClass?.equals(it.javaClass) ?: false) }.
                subscribe { supportFragmentManager.beginTransaction().replace(R.id.main_content, it).commit() }

        val fabClickObservable = fab.clicks()

        fabClickObservable.
                subscribe {
                    view ->
                    handleClick(view)
                }

    }

    private fun handleClick(view: Any?) {
        DetailActivity.startDetail(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        drawer.removeDrawerListener(toggle)
        unbind()
    }

    private fun unbind() {
        disposable.dispose()
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
