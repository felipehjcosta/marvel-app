package com.felipecosta.kotlinrxjavasample.listing.di

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.data.pojo.Thumbnail
import com.felipecosta.kotlinrxjavasample.listing.datamodel.DummyContentListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.presentation.ListingViewModel
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import java.util.*

@Module
class ListingModule {

    @ListingScope
    @Provides
    fun provideListingDataModel(): ListingDataModel = DummyContentListingDataModel()

    @ListingScope
    @Provides
    fun provideListingViewModel(listingDataModel: ListingDataModel) = ListingViewModel(AsyncCommand {
        Observable.fromCallable {
            val char = Character()
            char.id = 1009718
            char.name = "Wolverine"
            char.description = "Born with super-human senses and the power to heal from almost any wound, Wolverine was captured by a secret Canadian organization and given an unbreakable skeleton and claws. Treated like an animal, it took years for him to control himself. Now, he's a premiere member of both the X-Men and the Avengers."
            char.modified = Date() // "2014-06-10T16:13:25-0400",
            val thumbnail = Thumbnail()
            thumbnail.path = "http://i.annihil.us/u/prod/marvel/i/mg/2/60/537bcaef0f6cf"
            thumbnail.extension = "jpg"
            char.thumbnail = thumbnail
            char.resourceURI = "http://gateway.marvel.com/v1/public/characters/1009718"
            listOf(char)
        }
    })

}