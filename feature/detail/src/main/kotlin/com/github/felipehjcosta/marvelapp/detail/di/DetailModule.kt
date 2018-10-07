package com.github.felipehjcosta.marvelapp.detail.di

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

}
