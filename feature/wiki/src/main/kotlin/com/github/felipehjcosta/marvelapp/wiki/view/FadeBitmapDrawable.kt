/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.felipehjcosta.marvelapp.wiki.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Rect
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.SystemClock
import android.view.View

internal class FadeBitmapDrawable(
    resources: Resources,
    bitmap: Bitmap,
    private var placeholder: Drawable?,
    fade: Boolean
) : BitmapDrawable(resources, bitmap) {
    private var startTimeMillis: Long = 0L
    private var animating: Boolean = false

    private var internalAlpha = ALPHA

    init {
        if (fade) {
            animating = true
            startTimeMillis = SystemClock.uptimeMillis()
        }
    }

    override fun draw(canvas: Canvas) {
        if (!animating) {
            super.draw(canvas)
        } else {
            val normalized = (SystemClock.uptimeMillis() - startTimeMillis) / FADE_DURATION
            if (normalized >= 1.0f) {
                animating = false
                placeholder = null
                super.draw(canvas)
            } else {
                placeholder?.draw(canvas)

                // setAlpha will call invalidateSelf and drive the animation.
                val partialAlpha = (internalAlpha * normalized).toInt()
                super.setAlpha(partialAlpha)
                super.draw(canvas)
                super.setAlpha(internalAlpha)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        internalAlpha = alpha
        placeholder?.alpha = alpha
        super.setAlpha(alpha)
    }

    override fun setColorFilter(cf: ColorFilter?) {
        placeholder?.colorFilter = cf
        super.setColorFilter(cf)
    }

    override fun onBoundsChange(bounds: Rect) {
        placeholder?.bounds = bounds
        super.onBoundsChange(bounds)
    }

    companion object {
        private const val FADE_DURATION = 400.0f
        private const val ALPHA = 0xFF
    }
}

fun View?.background(bitmap: Bitmap, fade: Boolean = false) {
    this?.run {
        val placeholder = background
        if (placeholder is AnimationDrawable) {
            placeholder.stop()
        }

        background = FadeBitmapDrawable(resources, bitmap, placeholder, fade)
    }
}
