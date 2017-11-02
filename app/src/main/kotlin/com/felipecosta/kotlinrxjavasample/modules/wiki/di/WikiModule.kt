package com.felipecosta.kotlinrxjavasample.modules.wiki.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel.HighlightedCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel.OthersCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.HighlightedCharactersViewModel
import com.felipecosta.kotlinrxjavasample.modules.wiki.presentation.OthersCharactersViewModel
import dagger.Module
import dagger.Provides

@Module
class WikiModule {

    @WikiScope
    @Provides
    fun provideHighlightedCharactersDataModel(dataRepository: DataRepository): HighlightedCharactersDataModel =
            HighlightedCharactersDataModel(dataRepository)

    @WikiScope
    @Provides
    fun provideOthersCharactersDataModel(dataRepository: DataRepository): OthersCharactersDataModel =
            OthersCharactersDataModel(dataRepository)

    @WikiScope
    @Provides
    fun provideHighlightCharacterViewModel(dataModel: HighlightedCharactersDataModel): HighlightedCharactersViewModel =
            HighlightedCharactersViewModel(dataModel)

    @WikiScope
    @Provides
    fun provideOthersCharactersViewModel(dataModel: OthersCharactersDataModel): OthersCharactersViewModel =
            OthersCharactersViewModel(dataModel)
}