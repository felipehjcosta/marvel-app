package com.github.felipehjcosta.marvelapp.base

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.util.AppInjector
import com.github.felipehjcosta.marvelapp.base.di.DaggerApplicationComponent
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

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        component = createComponent()
        component.inject(this)
        AppInjector.init(this)
    }

    protected open fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> =
            AppInjector.decorateActivityAndroidInjector(dispatchingActivityAndroidInjector, component)

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            AppInjector.decorateFragmentAndroidInjector(dispatchingFragmentAndroidInjector, component)
}