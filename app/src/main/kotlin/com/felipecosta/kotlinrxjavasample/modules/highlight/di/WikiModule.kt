package com.felipecosta.kotlinrxjavasample.modules.highlight.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.modules.detail.di.WikiScope
import com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel.HighlightedCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel.OthersCharactersDataModel
import com.felipecosta.kotlinrxjavasample.modules.highlight.presentation.HighlightCharacterViewModel
import com.felipecosta.kotlinrxjavasample.modules.highlight.presentation.OthersCharactersViewModel
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
    fun provideHighlightCharacterViewModel(dataModel: HighlightedCharactersDataModel): HighlightCharacterViewModel =
            HighlightCharacterViewModel(dataModel)

    @WikiScope
    @Provides
    fun provideOthersCharactersViewModel(dataModel: OthersCharactersDataModel): OthersCharactersViewModel =
            OthersCharactersViewModel(dataModel)
}