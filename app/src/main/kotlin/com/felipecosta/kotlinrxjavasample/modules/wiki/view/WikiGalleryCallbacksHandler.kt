package com.felipecosta.kotlinrxjavasample.modules.wiki.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterItemViewModel
import com.felipecosta.kotlinrxjavasample.util.FastBlur
import com.felipecosta.kotlinrxjavasample.util.makeCubicGradientScrimDrawable
import com.github.felipehjcosta.layoutmanager.GalleryLayoutManager
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

    override fun onItemSelected(recyclerView: RecyclerView?, item: View, position: Int) {
        container.background = ColorDrawable(Color.TRANSPARENT)

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
                container.background = BitmapDrawable(container.resources, loadedImage)
            }
        })

    }

    private fun processBitmap(bitmap: Bitmap): Bitmap = Bitmap.
            createScaledBitmap(blur(bitmap), container.width, container.height, false)

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

    companion object {
        private const val SCALE_FACTOR = 4
        private const val BLUR_RADIUS = 5
    }
}
