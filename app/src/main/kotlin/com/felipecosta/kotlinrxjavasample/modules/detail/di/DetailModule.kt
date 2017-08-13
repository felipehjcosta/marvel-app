package com.felipecosta.kotlinrxjavasample.modules.detail.di

import android.content.Context
import android.content.SharedPreferences
import com.felipecosta.kotlinrxjavasample.R
import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.FavoriteRepository
import com.felipecosta.kotlinrxjavasample.data.SharedPreferencesStorage
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailContentDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.presentation.CharacterDetailViewModel
import com.felipecosta.kotlinrxjavasample.modules.detail.view.DetailActivity
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
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
    fun provideDetailDataModel(dataRepository: DataRepository): DetailDataModel = DetailContentDataModel(dataRepository)

    @DetailScope
    @Provides
    fun provideAsyncCommand(detailContentDataModel: DetailDataModel, characterId: Int): AsyncCommand<Character> = AsyncCommand {
        detailContentDataModel
                .character(characterId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    @DetailScope
    @Provides
    fun provideDiskDataRepository(context: Context, sharedPreferences: SharedPreferences): FavoriteRepository {
        val key = context.getString(R.string.saved_favorite_characters)
        val localStorage = SharedPreferencesStorage(key, sharedPreferences)

        return FavoriteRepository(localStorage)
    }

    @DetailScope
    @Provides
    fun provideDetailViewModel(asyncCommand: AsyncCommand<Character>, favoriteRepository: FavoriteRepository) = CharacterDetailViewModel(asyncCommand, favoriteRepository)
}