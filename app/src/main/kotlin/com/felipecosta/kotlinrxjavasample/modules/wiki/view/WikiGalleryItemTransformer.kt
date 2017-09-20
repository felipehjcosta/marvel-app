package com.felipecosta.kotlinrxjavasample.modules.wiki.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.felipecosta.kotlinrxjavasample.util.makeCubicGradientScrimDrawable
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager

class WikiGalleryItemTransformer : GalleryLayoutManager.ItemTransformer {

    override fun transformItem(layoutManager: GalleryLayoutManager, item: View, fraction: Float) {
        item.pivotX = item.width / 2.0f
        item.pivotY = item.height / 2.0f
        val scale = 1.0f - 0.3f * Math.abs(fraction)
        item.scaleX = scale
        item.scaleY = scale
        item.translationX = -item.width / 2.0f

        val selectableItemBackground = selectableItemBackground(item.context)

        val color = adjustColor(Color.BLACK, Math.abs(fraction))

        val layers = arrayOf(makeCubicGradientScrimDrawable(color, 5, Gravity.BOTTOM), selectableItemBackground)

        (item as FrameLayout).foreground = LayerDrawable(layers)
    }

    private fun selectableItemBackground(context: Context): Drawable {
        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        return ContextCompat.getDrawable(context, outValue.resourceId)
    }

    private fun adjustColor(color: Int, alphaFactor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * alphaFactor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }
}