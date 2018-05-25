package com.github.felipehjcosta.marvelapp.wiki.view

import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
import com.github.felipehjcosta.marvelapp.base.R
import com.github.felipehjcosta.marvelapp.base.util.FastBlur
import com.github.felipehjcosta.marvelapp.wiki.presentation.CharacterItemViewModel
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class WikiGalleryCallbacksHandler(private val items: List<CharacterItemViewModel>,
                                  private val container: ViewGroup) :
        GalleryLayoutManager.ItemTransformer, GalleryLayoutManager.OnItemSelectedListener {

    override fun transformItem(layoutManager: GalleryLayoutManager, item: View, viewPosition: Int, fraction: Float) {
        item.pivotX = item.width / 2.0f
        item.pivotY = item.height / 2.0f
        val scale = 1.0f - 0.3f * Math.abs(fraction)
        item.scaleX = scale
        item.scaleY = scale
        item.translationX = -item.width / 2.0f + item.resources.getDimensionPixelSize(R.dimen.gallery_item_padding_left)

        val layerDrawable = (item as FrameLayout).foreground as LayerDrawable

        val gradient = layerDrawable.getDrawable(0) as GradientDrawable
        val alphaFraction = 1.0f - Math.abs(fraction)
        val alpha = 255 - Math.round(255 * alphaFraction)
        gradient.alpha = alpha
    }

    override fun onItemSelected(recyclerView: RecyclerView?, item: View, position: Int) {
        val options = DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.image_default_color)
                .showImageForEmptyUri(R.color.image_default_color)
                .showImageOnFail(R.color.image_default_color)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .postProcessor { processBitmap(it) }
                .build()

        ImageLoader.getInstance().loadImage(items[position].image, options, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                super.onLoadingComplete(imageUri, view, loadedImage)
                container.background(loadedImage!!, true)
            }
        })

    }

    private fun processBitmap(bitmap: Bitmap): Bitmap = Bitmap.createScaledBitmap(addGradient(blur(bitmap)), container.width, container.height, false)

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

    fun addGradient(src: Bitmap, color1: Int = Color.TRANSPARENT, color2: Int = Color.BLACK): Bitmap {
        val result = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        Canvas(result).apply {
            drawBitmap(src, 0.0f, 0.0f, null)

            val paint = Paint().apply {
                shader = LinearGradient(0.0f, 0.0f, 0.0f, src.height.toFloat(), color1, color2, Shader.TileMode.CLAMP)
            }

            drawRect(0.0f, 0.0f, src.width.toFloat(), src.height.toFloat(), paint)
        }
        return result
    }

    companion object {
        private const val SCALE_FACTOR = 4
        private const val BLUR_RADIUS = 4
    }
}
