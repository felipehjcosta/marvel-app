package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.github.felipehjcosta.marvelapp.base.DemoApplication
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    private val activityMap =
        mutableMapOf<Class<out Activity>, (ApplicationComponent) -> AndroidInjector.Builder<out Activity>>()

    private val fragmentMap =
        mutableMapOf<Class<out Fragment>, (ApplicationComponent) -> AndroidInjector.Builder<out Fragment>>()

    internal fun init(demoApplication: DemoApplication) {
        demoApplication.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks by EmptyActivityLifecycleCallbacks() {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }
        })
    }

    private fun handleActivity(activity: Activity?) {
        if (activity is FragmentActivity) {
            try {
                AndroidInjection.inject(activity)
            } catch (e: IllegalArgumentException) {
                android.util.Log.i(
                    AppInjector::class.java.simpleName,
                    "Unable to inject activity: ${activity.javaClass.simpleName}",
                    e
                )
            }

            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(
                        fm: FragmentManager,
                        fragment: Fragment,
                        savedInstanceState: Bundle?
                    ) {
                        handleFragment(fragment)
                    }
                }, true
            )
        }
    }

    private fun handleFragment(fragment: Fragment?) {
        try {
            AndroidSupportInjection.inject(fragment)
        } catch (e: IllegalArgumentException) {
            android.util.Log.i(
                AppInjector::class.java.simpleName,
                "Unable to inject fragment: ${fragment?.javaClass?.simpleName}",
                e
            )
        }
    }

    fun <T : Activity> registerActivityBuilder(
        clazz: Class<T>,
        block: (ApplicationComponent) -> AndroidInjector.Builder<T>
    ) {
        activityMap[clazz] = block
    }

    fun <T : Fragment> registerFragmentBuilder(
        clazz: Class<T>,
        block: (ApplicationComponent) -> AndroidInjector.Builder<T>
    ) {
        fragmentMap[clazz] = block
    }

    internal fun decorateActivityAndroidInjector(
        androidInjector: AndroidInjector<Activity>,
        component: ApplicationComponent
    ): AndroidInjector<Activity> {
        return InstantAppsActivityInjector(component, activityMap, androidInjector)
    }

    internal fun decorateFragmentAndroidInjector(
        androidInjector: AndroidInjector<Fragment>,
        component: ApplicationComponent
    ): AndroidInjector<Fragment> {
        return InstantAppsFragmentInjector(component, fragmentMap, androidInjector)
    }
}
