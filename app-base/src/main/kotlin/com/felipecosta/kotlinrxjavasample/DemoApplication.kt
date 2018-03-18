package com.felipecosta.kotlinrxjavasample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.felipecosta.kotlinrxjavasample.di.ApplicationComponent
import com.felipecosta.kotlinrxjavasample.di.DaggerApplicationComponent
import com.felipecosta.kotlinrxjavasample.util.EmptyActivityLifecycleCallbacks
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
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

        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks by EmptyActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity is FragmentActivity) {
                    try {
                        AndroidInjection.inject(activity)
                    } catch (e: IllegalArgumentException) {
                        android.util.Log.i("DemoApplication", "Unable to inject activity: ${activity.javaClass.simpleName}", e)
                    }

                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                            object : FragmentManager.FragmentLifecycleCallbacks() {
                                override fun onFragmentCreated(fm: FragmentManager?,
                                                               fragment: Fragment?,
                                                               savedInstanceState: Bundle?) {
                                    try {
                                        AndroidSupportInjection.inject(fragment)
                                    } catch (e: IllegalArgumentException) {
                                        android.util.Log.i("DemoApplication", "Unable to inject fragment: ${fragment?.javaClass?.simpleName}", e)
                                    }
                                }
                            }, true)
                }
            }
        })

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