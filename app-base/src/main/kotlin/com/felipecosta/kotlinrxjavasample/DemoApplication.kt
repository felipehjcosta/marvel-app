package com.felipecosta.kotlinrxjavasample

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.DaggerApplicationComponent
import com.felipecosta.kotlinrxjavasample.util.AppInjector
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class DemoApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingFragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        val component = createComponent()
        component.inject(this)

        AppInjector.init(this)

        initImageLoader()
    }

    private fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(this).build()
        ImageLoader.getInstance().init(config)
    }

    open protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityAndroidInjector

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingFragmentAndroidInjector
}