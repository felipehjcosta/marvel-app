package com.github.felipehjcosta.marvelapp.base.imageloader

import android.graphics.Bitmap
import android.widget.ImageView

interface ImageLoader {
    fun loadRoundedImage(url: String, imageView: ImageView, cornerRadius: Int)

    fun loadImage(url: String,
                  imageView: ImageView)

    fun loadImage(url: String,
                  transformation: (Bitmap) -> Bitmap = { it },
                  onBitmapLoaded: (Bitmap?) -> Unit)
}