package com.felipecosta.kotlinrxjavasample.detail.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.detail.datamodel.DetailContentDataModel
import com.felipecosta.kotlinrxjavasample.detail.datamodel.DetailDataModel
import dagger.Module
import dagger.Provides

@Module
class DetailModule {
    @DetailScope
    @Provides
    fun provideDetailDataModel(detailDataModel: DetailDataModel) = DetailContentDataModel(DataRepository())
}
