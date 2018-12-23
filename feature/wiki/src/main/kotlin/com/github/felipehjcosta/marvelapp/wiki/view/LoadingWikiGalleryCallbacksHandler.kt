package com.github.felipehjcosta.marvelapp.wiki.view

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.widget.FrameLayout
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import com.github.felipehjcosta.marvelapp.base.R

class LoadingWikiGalleryCallbacksHandler : GalleryLayoutManager.ItemTransformer {

    override fun transformItem(
        layoutManager: GalleryLayoutManager,
        item: View,
        viewPosition: Int,
        fraction: Float
    ) {
        item.pivotX = item.width / 2.0f
        item.pivotY = item.height / 2.0f
        val scale = 1.0f - SCALE_MULTIPLE * Math.abs(fraction)
        item.scaleX = scale
        item.scaleY = scale
        item.translationX = -item.width / 2.0f +
                item.resources.getDimensionPixelSize(R.dimen.gallery_item_padding_left)

        val layerDrawable = (item as FrameLayout).foreground as LayerDrawable

        val gradient = layerDrawable.getDrawable(0) as GradientDrawable
        val alphaFraction = 1.0f - Math.abs(fraction)
        val alpha = WHITE_COLOR_RGB - Math.round(WHITE_COLOR_RGB * alphaFraction)
        gradient.alpha = alpha
    }

    companion object {
        private const val WHITE_COLOR_RGB = 255
        private const val SCALE_MULTIPLE = 0.3f
    }
}
