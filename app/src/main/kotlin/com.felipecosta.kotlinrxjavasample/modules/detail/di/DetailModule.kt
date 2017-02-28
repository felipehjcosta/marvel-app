package com.felipecosta.kotlinrxjavasample.modules.detail.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailContentDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.presentation.CharacterDetailViewModel
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class DetailModule(val characterId: Int) {
    @DetailScope
    @Provides
    fun provideDetailDataModel(dataRepository: DataRepository): DetailDataModel = DetailContentDataModel(dataRepository)

    @DetailScope
    @Provides
    fun provideAsyncCommand(detailContentDataModel: DetailDataModel): AsyncCommand<Character> = AsyncCommand {
        detailContentDataModel
                .character(characterId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    @DetailScope
    @Provides
    fun provideDetailViewModel(asyncCommand: AsyncCommand<Character>) = CharacterDetailViewModel(asyncCommand)
}
