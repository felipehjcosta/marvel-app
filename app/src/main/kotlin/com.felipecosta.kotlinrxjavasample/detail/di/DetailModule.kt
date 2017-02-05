package com.felipecosta.kotlinrxjavasample.detail.di

import com.felipecosta.kotlinrxjavasample.data.NetworkDataRepository
import com.felipecosta.kotlinrxjavasample.detail.datamodel.DetailContentDataModel
import com.felipecosta.kotlinrxjavasample.detail.datamodel.DetailDataModel
import dagger.Module
import dagger.Provides

@Module
class DetailModule {
    @DetailScope
    @Provides
    fun provideDetailDataModel(): DetailDataModel = DetailContentDataModel(NetworkDataRepository())
}
