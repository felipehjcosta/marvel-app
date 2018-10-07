package com.github.felipehjcosta.marvelapp.base.navigator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.net.toUri

internal class UrlBasedAppNavigator : AppNavigator {

    override fun showWiki(activity: Activity) {
        "https://marvelapp.instantappsample.com/main".toUri().startDeepLink(activity)
    }

    override fun showList(activity: Activity) {
        "https://marvelapp.instantappsample.com/listing".toUri().startDeepLink(activity)
    }

    override fun showDetail(activity: Activity, id: Int) {
        "https://marvelapp.instantappsample.com/detail/$id".toUri().startDeepLink(activity)
    }

    private fun Uri.startDeepLink(activity: Activity) {
        Intent(Intent.ACTION_VIEW, this).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            `package` = activity.packageName
            activity.startActivity(this)
        }
    }
}