package com.github.felipehjcosta.marvelapp.base

import android.app.Activity
import android.app.Application
import android.os.Looper
import androidx.fragment.app.Fragment
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import com.github.felipehjcosta.marvelapp.base.di.DaggerApplicationComponent
import com.github.felipehjcosta.marvelapp.base.network.BASE_URL
import com.github.felipehjcosta.marvelapp.base.util.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

open class MarvelAppApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var dispatchingFragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setupAndroidSchedulers()
        component = createComponent()
        component.inject(this)
        AppInjector.init(this)
    }

    private fun setupAndroidSchedulers() {
        val asyncMainThreadScheduler = AndroidSchedulers.from(Looper.getMainLooper(), true)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { asyncMainThreadScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { asyncMainThreadScheduler }
    }

    protected open fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().application(this).baseUrl(BASE_URL).build()
    }

    override fun activityInjector(): AndroidInjector<Activity> =
            AppInjector.decorateActivityAndroidInjector(dispatchingActivityAndroidInjector, component)

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            AppInjector.decorateFragmentAndroidInjector(dispatchingFragmentAndroidInjector, component)
}