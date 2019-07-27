package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjection

object AppInjector {

    private val activityMap by lazy {
        mutableMapOf<Class<out Activity>, (ApplicationComponent) -> AndroidInjector.Builder<out Activity>>()
    }

    private val fragmentMap by lazy {
        mutableMapOf<Class<out Fragment>, (ApplicationComponent) -> AndroidInjector.Builder<out Fragment>>()
    }

    internal fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
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

    internal fun decorateAndroidInjector(
            androidInjector: AndroidInjector<Any>,
            component: ApplicationComponent
    ): AndroidInjector<Any> {
        return InstantAppsAndroidInjector(component, activityMap, fragmentMap, androidInjector)
    }
}
