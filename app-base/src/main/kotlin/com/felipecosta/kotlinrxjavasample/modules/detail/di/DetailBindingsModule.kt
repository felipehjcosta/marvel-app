package com.felipecosta.kotlinrxjavasample.modules.detail.di

import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailContentDataModel
import com.felipecosta.kotlinrxjavasample.modules.detail.datamodel.DetailDataModel
import dagger.Binds
import dagger.Module

@Module
interface DetailBindingsModule {
    @Binds
    fun provideDetailDataModel(detailContentDataModel: DetailContentDataModel): DetailDataModel

}