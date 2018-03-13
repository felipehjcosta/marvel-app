package com.github.felipehjcosta.appdetail.di

import com.github.felipehjcosta.appdetail.datamodel.DetailContentDataModel
import com.github.felipehjcosta.appdetail.datamodel.DetailDataModel
import dagger.Binds
import dagger.Module

@Module
interface DetailBindingsModule {
    @Binds
    fun provideDetailDataModel(detailContentDataModel: DetailContentDataModel): DetailDataModel

}