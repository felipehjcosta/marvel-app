package com.felipecosta.kotlinrxjavasample.modules.listing.di

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.di.IOScheduler
import com.felipecosta.kotlinrxjavasample.di.MainScheduler
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingContentDataModel
import com.felipecosta.kotlinrxjavasample.modules.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.modules.listing.presentation.CharacterListViewModel
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler

@Module
class ListingModule {

    @ListingScope
    @Provides
    fun provideListingDataModel(dataRepository: DataRepository): ListingDataModel {
        return ListingContentDataModel(dataRepository)
    }

    @ListingScope
    @Provides
    fun provideListingViewModel(listingDataModel: ListingDataModel,
                                @IOScheduler ioScheduler: Scheduler,
                                @MainScheduler mainScheduler: Scheduler):
            CharacterListViewModel = CharacterListViewModel(listingDataModel)

}