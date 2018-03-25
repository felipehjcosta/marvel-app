package com.github.felipehjcosta.marvelapp.base.modules.listing.di

import com.github.felipehjcosta.marvelapp.base.modules.listing.datamodel.ListingContentDataModel
import com.github.felipehjcosta.marvelapp.base.modules.listing.datamodel.ListingDataModel
import dagger.Binds
import dagger.Module

@Module
abstract class ListingModule {

    @Binds
    abstract fun provideListingDataModel(listingContentDataModel: ListingContentDataModel): ListingDataModel

}