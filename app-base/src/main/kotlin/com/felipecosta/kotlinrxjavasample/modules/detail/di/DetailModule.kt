package com.felipecosta.kotlinrxjavasample.modules.detail.di

import android.content.Context
import android.content.SharedPreferences
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.FavoriteRepository
import com.felipecosta.kotlinrxjavasample.data.SharedPreferencesStorage
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
import com.felipecosta.rxaction.RxAction
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class DetailModule {

    @DetailScope
    @Provides
    fun providesCharacterId(detailActivity: DetailActivity) = with(detailActivity.intent) {
        if (extras != null && extras.containsKey(DetailActivity.CHARACTER_ID)) {
            extras.getInt(DetailActivity.CHARACTER_ID)
        } else {
            -1
        }
    }


    @DetailScope
    @Provides
    fun provideDiskDataRepository(context: Context, sharedPreferences: SharedPreferences): FavoriteRepository {
        val key = context.getString(R.string.saved_favorite_characters)
        val localStorage = SharedPreferencesStorage(key, sharedPreferences)

        return FavoriteRepository(localStorage)
    }
}
