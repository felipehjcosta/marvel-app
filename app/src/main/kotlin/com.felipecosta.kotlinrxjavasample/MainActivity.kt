package com.felipecosta.kotlinrxjavasample

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.felipecosta.kotlinrxjavasample.rx.itemSelections
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var navigationView: NavigationView

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
    }

    private fun bind() {
        disposable = navigationView.itemSelections().
                doOnNext { drawer.closeDrawer(GravityCompat.START) }.
                //                doOnNext { it.isChecked = true }.
                subscribe { Log.e(MainActivity::class.simpleName, ">>>> subscribe: $it") }
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
