package com.felipecosta.kotlinrxjavasample.util

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.util.LruCache
import android.support.v7.widget.RecyclerView
import android.view.View

inline fun <reified T : View> Activity.bindView(@IdRes idRes: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) as T }
}

inline fun <reified T : View> Fragment.bindView(@IdRes idRes: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { view?.findViewById<T>(idRes) as T }
}

inline fun <reified T : View> View.bindView(@IdRes idRes: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }
}

inline fun <reified T : View> RecyclerView.ViewHolder.bindView(@IdRes idRes: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById<T>(idRes) }
}

inline fun <reified T : View> View?.findBy(@IdRes res: Int): T = this?.findViewById<T>(res) as T
inline fun <reified T : View> Activity?.findBy(@IdRes res: Int): T = this?.findViewById<T>(res) as T

operator fun <K, V> LruCache<K, V>.set(key: K?, value: V?): Unit {
    this.put(key, value)
}