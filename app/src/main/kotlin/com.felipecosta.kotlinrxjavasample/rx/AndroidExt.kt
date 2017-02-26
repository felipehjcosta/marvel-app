package com.felipecosta.kotlinrxjavasample.rx

import android.app.Activity
import android.support.annotation.IntegerRes
import android.support.v4.util.LruCache
import android.view.View

inline fun <reified T : View> View?.findBy(@IntegerRes res: Int): T = this?.findViewById(res) as T
inline fun <reified T : View> Activity?.findBy(@IntegerRes res: Int): T = this?.findViewById(res) as T

operator fun <K, V> LruCache<K, V>.set(key: K?, value: V?): Unit {
    this.put(key, value)
}