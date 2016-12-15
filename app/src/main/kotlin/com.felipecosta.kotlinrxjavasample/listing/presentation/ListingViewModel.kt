package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import io.reactivex.Observable

class ListingViewModel(private val dataModel: ListingDataModel) {

    fun items(): Observable<List<DummyContent.DummyItem>> = dataModel.items()

}