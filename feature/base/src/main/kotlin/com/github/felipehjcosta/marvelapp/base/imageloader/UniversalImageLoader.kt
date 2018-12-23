package com.github.felipehjcosta.marvelapp.base.imageloader

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.github.felipehjcosta.marvelapp.base.R
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class UniversalImageLoader(
    private val imageLoader: com.nostra13.universalimageloader.core.ImageLoader
) : ImageLoader {
    override fun loadRoundedImage(url: String, imageView: ImageView, cornerRadius: Int) {

        val imageOptions = DisplayImageOptions.Builder()
            .displayer(RoundedBitmapDisplayer(cornerRadius))
            .showImageOnLoading(R.drawable.ic_rounded_image_default)
            .showImageForEmptyUri(R.drawable.ic_rounded_image_default)
            .showImageOnFail(R.drawable.ic_rounded_image_default)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build()

        imageLoader.displayImage(url, imageView, imageOptions)
    }

    override fun loadImage(url: String, imageView: ImageView) {
        val imageOptions = DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.image_default_color)
            .showImageForEmptyUri(R.color.image_default_color)
            .showImageOnFail(R.color.image_default_color)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build()

        imageLoader.displayImage(url, imageView, imageOptions)
    }

    override fun loadImage(
        url: String,
        transformation: (Bitmap) -> Bitmap,
        onBitmapLoaded: (Bitmap?) -> Unit
    ) {
        val options = DisplayImageOptions.Builder()
            .showImageOnLoading(R.color.image_default_color)
            .showImageForEmptyUri(R.color.image_default_color)
            .showImageOnFail(R.color.image_default_color)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .postProcessor { transformation(it) }
            .build()

        imageLoader.loadImage(url, options, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                super.onLoadingComplete(imageUri, view, loadedImage)
                onBitmapLoaded(loadedImage)
            }
        })
    }
}
