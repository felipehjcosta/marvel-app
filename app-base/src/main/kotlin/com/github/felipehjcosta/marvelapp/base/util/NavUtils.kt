package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import android.content.Intent
import androidx.net.toUri

fun navigateToDetail(activity: Activity, id: Int) {
    val uri = "https://marvelapp.instantappsample.com/detail/${id}".toUri()
    Intent(Intent.ACTION_VIEW, uri).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
        `package` = activity.packageName
        activity.startActivity(this)
    }
}

fun navigateToListing(activity: Activity) {
    val uri = "https://marvelapp.instantappsample.com/listing".toUri()
    Intent(Intent.ACTION_VIEW, uri).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
        `package` = activity.packageName
        activity.startActivity(this)
    }
}
