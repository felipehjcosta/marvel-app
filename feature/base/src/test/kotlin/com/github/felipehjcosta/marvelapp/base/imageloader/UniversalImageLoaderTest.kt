package com.github.felipehjcosta.marvelapp.base.imageloader

import android.graphics.Bitmap
import android.widget.ImageView
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import kotlin.test.assertEquals

class UniversalImageLoaderTest {

    private val mockUniversalImageLoader = mockk<com.nostra13.universalimageloader.core.ImageLoader>(relaxed = true)

    private val imageLoader = UniversalImageLoader(mockUniversalImageLoader)

    @Test
    fun ensureLoadImageCallsImageLoaderLibraryDisplayImage() {
        val mockImageView = mockk<ImageView>()
        val url = "http://i.annihil.us/u/prod/marvel/i/mg/3/b0/5261a7e53f827.jpg"

        imageLoader.loadImage(url, mockImageView)
        verify { mockUniversalImageLoader.displayImage(url, mockImageView, any<DisplayImageOptions>()) }
    }

    @Test
    fun ensureLoadImageCallsImageLoaderLibraryLoadImageWithCallback() {
        val url = "http://i.annihil.us/u/prod/marvel/i/mg/3/b0/5261a7e53f827.jpg"

        val mockOnBitmapLoaded = mockk<(Bitmap?) -> Unit>(relaxed = true)
        imageLoader.loadImage(url, mockk(), mockOnBitmapLoaded)
        val slot = slot<SimpleImageLoadingListener>()
        verify { mockUniversalImageLoader.loadImage(url, any<DisplayImageOptions>(), capture(slot)) }
        val mockLoadedImage = mockk<Bitmap>()
        slot.captured.onLoadingComplete(url, mockk(), mockLoadedImage)
        verify { mockOnBitmapLoaded.invoke(mockLoadedImage) }
    }

    @Test
    fun ensureLoadRoundedImageCallsImageLoaderLibraryDisplayImageWithRoundedConfig() {
        val mockImageView = mockk<ImageView>()
        val url = "http://i.annihil.us/u/prod/marvel/i/mg/3/b0/5261a7e53f827.jpg"

        imageLoader.loadRoundedImage(url, mockImageView, 42)
        val slot = slot<DisplayImageOptions>()
        verify { mockUniversalImageLoader.displayImage(url, mockImageView, capture(slot)) }
        assertEquals(RoundedBitmapDisplayer::class.java, slot.captured.displayer::class.java)
    }
}