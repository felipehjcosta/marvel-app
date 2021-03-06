package com.github.felipehjcosta.marvelapp.wiki.view

import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import com.github.felipehjcosta.marvelapp.base.R
import com.github.felipehjcosta.marvelapp.base.imageloader.ImageLoader
import com.github.felipehjcosta.marvelapp.base.util.FastBlur
import com.github.felipehjcosta.marvelapp.wiki.presentation.CharacterItemViewModel

class WikiGalleryCallbacksHandler(
    private val imageLoader: ImageLoader,
    private val items: List<CharacterItemViewModel>,
    private val container: ViewGroup
) : GalleryLayoutManager.ItemTransformer, GalleryLayoutManager.OnItemSelectedListener {

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

    override fun onItemSelected(recyclerView: RecyclerView?, item: View, position: Int) {
        imageLoader.loadImage(items[position].image, this::processBitmap) {
            container.background(it!!, true)
        }
    }

    private fun processBitmap(bitmap: Bitmap): Bitmap = Bitmap.createScaledBitmap(
        addGradient(blur(bitmap)),
        container.width,
        container.height,
        false
    )

    private fun blur(bitmap: Bitmap): Bitmap {

        val scaledWidth = bitmap.width / SCALE_FACTOR
        val scaledHeight = bitmap.height / SCALE_FACTOR

        val overlay = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
        Canvas(overlay).apply {
            scale(1.0f / SCALE_FACTOR, 1.0f / SCALE_FACTOR)
            drawBitmap(bitmap, 0.0f, 0.0f, Paint(Paint.FILTER_BITMAP_FLAG))
        }

        return FastBlur.doBlur(overlay, BLUR_RADIUS, true)
    }

    private fun addGradient(
        src: Bitmap,
        color1: Int = Color.TRANSPARENT,
        color2: Int = Color.BLACK
    ): Bitmap {
        val result = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        Canvas(result).apply {
            drawBitmap(src, 0.0f, 0.0f, null)

            val paint = Paint().apply {
                shader = LinearGradient(
                    0.0f,
                    0.0f,
                    0.0f,
                    src.height.toFloat(),
                    color1,
                    color2,
                    Shader.TileMode.CLAMP
                )
            }

            drawRect(0.0f, 0.0f, src.width.toFloat(), src.height.toFloat(), paint)
        }
        return result
    }

    companion object {
        private const val SCALE_FACTOR = 4
        private const val BLUR_RADIUS = 4

        private const val WHITE_COLOR_RGB = 255
        private const val SCALE_MULTIPLE = 0.3f
    }
}
