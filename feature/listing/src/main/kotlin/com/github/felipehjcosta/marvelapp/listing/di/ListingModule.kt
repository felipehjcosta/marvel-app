package com.github.felipehjcosta.marvelapp.listing.di

import com.github.felipehjcosta.marvelapp.listing.datamodel.ListingContentDataModel
import com.github.felipehjcosta.marvelapp.listing.datamodel.ListingDataModel
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModel
import com.github.felipehjcosta.marvelapp.listing.presentation.CharacterListViewModelInputOutput
import dagger.Binds
import dagger.Module

@Module
abstract class ListingModule {

    @Binds
    abstract fun provideListingDataModel(listingContentDataModel: ListingContentDataModel): ListingDataModel

    @Binds
    abstract fun provideViewModel(characterListViewModelInputOutput: CharacterListViewModelInputOutput): CharacterListViewModel

}
