package com.felipecosta.kotlinrxjavasample.modules.listing.di

import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingContentDataModel
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import dagger.Binds
import dagger.Module

@Module
abstract class ListingModule {

    @Binds
    abstract fun provideListingDataModel(listingContentDataModel: ListingContentDataModel): ListingDataModel

}