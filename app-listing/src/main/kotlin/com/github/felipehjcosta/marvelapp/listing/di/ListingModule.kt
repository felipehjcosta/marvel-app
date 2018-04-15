package com.github.felipehjcosta.marvelapp.listing.di

import com.github.felipehjcosta.marvelapp.listing.datamodel.ListingContentDataModel
import com.github.felipehjcosta.marvelapp.listing.datamodel.ListingDataModel
import dagger.Binds
import dagger.Module

@Module
abstract class ListingModule {

    @Binds
    abstract fun provideListingDataModel(listingContentDataModel: ListingContentDataModel): ListingDataModel

}