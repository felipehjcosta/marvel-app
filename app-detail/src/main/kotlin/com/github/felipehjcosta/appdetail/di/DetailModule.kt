package com.github.felipehjcosta.appdetail.di

import android.content.SharedPreferences
import com.felipecosta.kotlinrxjavasample.data.FavoriteRepository
import com.felipecosta.kotlinrxjavasample.data.SharedPreferencesStorage
import com.github.felipehjcosta.appdetail.view.DetailActivity
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
