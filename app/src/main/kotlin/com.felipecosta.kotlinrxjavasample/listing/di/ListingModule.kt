package com.felipecosta.kotlinrxjavasample.listing.di

import com.felipecosta.kotlinrxjavasample.listing.datamodel.DummyContentListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.presentation.ListingViewModel
import dagger.Module
import dagger.Provides

@Module
class ListingModule {

    @ListingScope
    @Provides
    fun provideListingDataModel(): ListingDataModel = DummyContentListingDataModel()

    @ListingScope
    @Provides
    fun provideListingViewModel(listingDataModel: ListingDataModel)= ListingViewModel(listingDataModel)

}