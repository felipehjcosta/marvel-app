package com.github.felipehjcosta.marvelapp.base.di

import android.content.Context
import android.content.SharedPreferences
import com.github.felipehjcosta.marvelapp.base.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = context
            .getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

}
