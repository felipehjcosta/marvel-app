package com.github.felipehjcosta.marvelapp.base.navigator

import android.app.Activity

interface AppNavigator {
    fun showWiki(activity: Activity)
    fun showList(activity: Activity)
    fun showDetail(activity: Activity, id: Int)
}