package com.github.felipehjcosta.marvelapp.base.character.data

import android.content.SharedPreferences
import androidx.content.edit

class SharedPreferencesStorage(private val key: String,
                               private val sharedPreferences: SharedPreferences) : LocalStorage {

    override var storageValue: String
        get() = sharedPreferences.getString(key, "")
        set(value) = sharedPreferences.edit { putString(key, value) }

}