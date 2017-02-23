package com.felipecosta.kotlinrxjavasample.data

import android.content.Context
import com.felipecosta.kotlinrxjavasample.R

abstract class PreferencesRepository(val context: Context) {
    fun put(key: String, value: String) {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun get(key: String): String {
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        return sharedPref.getString(key, "")
    }
}