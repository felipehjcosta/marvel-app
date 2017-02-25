package com.felipecosta.kotlinrxjavasample.rx

import android.app.Activity
import android.support.annotation.IntegerRes
import android.view.View

inline fun <reified T : View> View?.findBy(@IntegerRes res: Int): T = this?.findViewById(res) as T
inline fun <reified T : View> Activity?.findBy(@IntegerRes res: Int): T = this?.findViewById(res) as T
