package com.github.felipehjcosta.marvelapp.detail.di

import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailContentDataModel
import com.github.felipehjcosta.marvelapp.detail.datamodel.DetailDataModel
import dagger.Binds
import dagger.Module

@Module
interface DetailBindingsModule {
    @Binds
    fun provideDetailDataModel(detailContentDataModel: DetailContentDataModel): DetailDataModel

}
