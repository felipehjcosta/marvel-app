package com.github.felipehjcosta.marvelapp.detail.di

import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailContentDataModel
import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailDataModel
import com.github.felipehjcosta.marvelapp.detail.presentation.CharacterDetailViewModel
import com.github.felipehjcosta.marvelapp.detail.presentation.CharacterDetailViewModelInputOutput
import dagger.Binds
import dagger.Module

@Module
interface DetailBindingsModule {
    @Binds
    fun provideDetailDataModel(detailContentDataModel: DetailContentDataModel): DetailDataModel

    @Binds
    fun provideViewModel(
        characterDetailViewModelInputOutput: CharacterDetailViewModelInputOutput
    ): CharacterDetailViewModel
}
