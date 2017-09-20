/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.felipecosta.kotlinrxjavasample.util

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.support.v4.util.LruCache
import android.view.Gravity

private val cubicGradientScrimCache = LruCache<Int, Drawable>(10)

/**
 * Creates an approximated cubic gradient using a multi-stop linear gradient. See
 * <a href="https://plus.google.com/+RomanNurik/posts/2QvHVFWrHZf">this post</a> for more
 * details.
 */
fun makeCubicGradientScrimDrawable(baseColor: Int, numStops: Int, gravity: Int): Drawable {
    var numStops = numStops

    // Generate a cache key by hashing together the inputs, based on the method described in the Effective Java book
    var cacheKeyHash = baseColor
    cacheKeyHash = 31 * cacheKeyHash + numStops
    cacheKeyHash = 31 * cacheKeyHash + gravity

    val cachedGradient = cubicGradientScrimCache.get(cacheKeyHash)
    if (cachedGradient != null) {
        return cachedGradient
    }

    numStops = Math.max(numStops, 2)

    val paintDrawable = PaintDrawable()
    paintDrawable.shape = RectShape()

    val stopColors = IntArray(numStops)

    val red = Color.red(baseColor)
    val green = Color.green(baseColor)
    val blue = Color.blue(baseColor)
    val alpha = Color.alpha(baseColor)

    for (i in 0..numStops - 1) {
        val x = i * 1f / (numStops - 1)
        val opacity = constrain(0.0f, 1.0f, Math.pow(x.toDouble(), 3.0).toFloat())
        stopColors[i] = Color.argb((alpha * opacity).toInt(), red, green, blue)
    }

    val x0: Float
    val x1: Float
    val y0: Float
    val y1: Float
    when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
        Gravity.LEFT -> {
            x0 = 1f
            x1 = 0f
        }
        Gravity.RIGHT -> {
            x0 = 0f
            x1 = 1f
        }
        else -> {
            x0 = 0f
            x1 = 0f
        }
    }
    when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
        Gravity.TOP -> {
            y0 = 1f
            y1 = 0f
        }
        Gravity.BOTTOM -> {
            y0 = 0f
            y1 = 1f
        }
        else -> {
            y0 = 0f
            y1 = 0f
        }
    }

    paintDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
        override fun resize(width: Int, height: Int): Shader {
            return LinearGradient(
                    width * x0,
                    height * y0,
                    width * x1,
                    height * y1,
                    stopColors, null,
                    Shader.TileMode.CLAMP)
        }
    }

    cubicGradientScrimCache.put(cacheKeyHash, paintDrawable)
    return paintDrawable
}



