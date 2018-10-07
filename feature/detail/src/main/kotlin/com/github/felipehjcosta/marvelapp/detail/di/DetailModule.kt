package com.github.felipehjcosta.marvelapp.detail.di

import android.content.SharedPreferences
import com.github.felipehjcosta.marvelapp.base.character.data.FavoriteRepository
import com.github.felipehjcosta.marvelapp.base.character.data.SharedPreferencesStorage
import com.github.felipehjcosta.marvelapp.detail.view.DetailActivity
import dagger.Module
import dagger.Provides

@Module
class DetailModule {

    @DetailScope
    @Provides
    fun providesCharacterId(detailActivity: DetailActivity) = with(detailActivity.intent) {
        try {
            detailActivity.intent.data.lastPathSegment.toInt()
        } catch (e: Exception) {
            -1
        }
    }


    @DetailScope
    @Provides
    fun provideDiskDataRepository(sharedPreferences: SharedPreferences): FavoriteRepository {
        val key = "saved_favorite_character"
        val localStorage = SharedPreferencesStorage(key, sharedPreferences)

        return FavoriteRepository(localStorage)
    }
}
