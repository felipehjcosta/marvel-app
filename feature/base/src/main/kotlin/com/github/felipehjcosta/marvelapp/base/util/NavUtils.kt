package com.github.felipehjcosta.marvelapp.base.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.net.toUri

fun navigateToDetail(activity: Activity, id: Int) {
    "https://marvelapp.instantappsample.com/detail/$id".toUri().startDeepLink(activity)
}

fun navigateToListing(activity: Activity) {
    "https://marvelapp.instantappsample.com/listing".toUri().startDeepLink(activity)
}

fun navigateToMain(activity: Activity) {
    "https://marvelapp.instantappsample.com/main".toUri().startDeepLink(activity)
}

private fun Uri.startDeepLink(activity: Activity) {
    Intent(Intent.ACTION_VIEW, this).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
        `package` = activity.packageName
        activity.startActivity(this)
    }
}
