package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import android.util.LruCache
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

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

operator fun <K, V> LruCache<K, V>.set(key: K, value: V): Unit {
    this.put(key, value)
}
