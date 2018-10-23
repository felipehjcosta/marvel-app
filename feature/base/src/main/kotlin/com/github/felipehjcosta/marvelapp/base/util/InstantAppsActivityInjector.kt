package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import dagger.android.AndroidInjector

class InstantAppsActivityInjector(
        private val applicationComponent: ApplicationComponent,
        private val map: MutableMap<Class<out Activity>,
                (ApplicationComponent) -> AndroidInjector.Builder<out Activity>>,
        private val decoratedAndroidInjector: AndroidInjector<Activity>
) : AndroidInjector<Activity> by decoratedAndroidInjector {

    override fun inject(instance: Activity?) {
        if (map.contains(instance?.activityClass())) {
            inject(instance, instance?.activityClass()!!)
        } else {
            decoratedAndroidInjector.inject(instance)
        }
    }

    private inline fun <reified T : Activity> inject(instance: Activity?, clazz: Class<out T>) {
        val builder = map[clazz]?.invoke(applicationComponent) as? AndroidInjector.Builder<Activity>
        builder?.create(instance.cast())?.inject(instance.cast())
    }

    private fun Activity?.activityClass(): Class<out Activity>? = this?.javaClass

    private inline fun <reified T : Activity> Activity?.cast(): T? = this as? T
}
