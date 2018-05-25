package com.github.felipehjcosta.marvelapp.base.data

import android.content.Context
import com.github.felipehjcosta.marvelapp.base.R

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