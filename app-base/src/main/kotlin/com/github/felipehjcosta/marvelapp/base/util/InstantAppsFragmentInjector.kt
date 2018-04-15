package com.github.felipehjcosta.marvelapp.base.util

import android.support.v4.app.Fragment
import com.github.felipehjcosta.marvelapp.base.di.ApplicationComponent
import dagger.android.AndroidInjector
import kotlin.reflect.full.cast

class InstantAppsFragmentInjector(
        private val applicationComponent: ApplicationComponent,
        private val map: MutableMap<Class<out Fragment>,
                (ApplicationComponent) -> AndroidInjector.Builder<out Fragment>>,
        private val decoratedAndroidInjector: AndroidInjector<Fragment>
) : AndroidInjector<Fragment> by decoratedAndroidInjector {

    override fun inject(instance: Fragment?) {
        if (map.contains(instance?.activityClass())) {
            inject(instance, instance?.activityClass()!!)
        } else {
            decoratedAndroidInjector.inject(instance)
        }
    }

    private inline fun <reified T : Fragment> inject(instance: Fragment?, clazz: Class<out T>) {
        val builder = map[clazz]?.invoke(applicationComponent) as? AndroidInjector.Builder<Fragment>
        builder?.create(instance.cast())?.inject(instance.cast())
    }

    private fun Fragment?.activityClass(): Class<out Fragment>? = this?.javaClass

    private inline fun <reified T : Fragment> Fragment?.cast(): T? = T::class.cast(this)
}
