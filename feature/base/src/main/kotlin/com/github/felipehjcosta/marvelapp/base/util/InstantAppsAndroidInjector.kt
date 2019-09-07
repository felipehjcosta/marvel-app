package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import androidx.fragment.app.Fragment
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import dagger.android.AndroidInjector

class InstantAppsAndroidInjector(
        private val applicationComponent: ApplicationComponent,
        private val activityMap: MutableMap<Class<out Activity>,
                (ApplicationComponent) -> AndroidInjector.Builder<out Activity>>,
        private val fragmentMap: MutableMap<Class<out Fragment>,
                (ApplicationComponent) -> AndroidInjector.Builder<out Fragment>>,
        private val decoratedAndroidInjector: AndroidInjector<Any>

) : AndroidInjector<Any> {

    override fun inject(instance: Any?) {
        if (activityMap.contains(instance.activityClass())) {
            inject(instance as Activity, instance.activityClass()!!)
        } else if (fragmentMap.contains(instance.fragmentClass())) {
            inject(instance as Fragment, instance.fragmentClass()!!)
        } else {
            decoratedAndroidInjector.inject(instance)
        }
    }

    private inline fun <reified T : Activity> inject(instance: Activity?, clazz: Class<out T>) {
        val builder = activityMap[clazz]?.invoke(applicationComponent) as? AndroidInjector.Builder<Activity>
        builder?.create(instance.cast())?.inject(instance.cast())
    }

    private fun Any?.activityClass(): Class<out Activity>? = this?.javaClass as? Class<out Activity>?

    private inline fun <reified T : Activity> Activity?.cast(): T? = this as? T

    private inline fun <reified T : Fragment> inject(instance: Fragment?, clazz: Class<out T>) {
        val builder = fragmentMap[clazz]?.invoke(applicationComponent) as? AndroidInjector.Builder<Fragment>
        builder?.create(instance.cast())?.inject(instance.cast())
    }

    private fun Any?.fragmentClass(): Class<out Fragment>? = this?.javaClass as? Class<out Fragment>?

    private inline fun <reified T : Fragment> Fragment?.cast(): T? = this as? T

}
