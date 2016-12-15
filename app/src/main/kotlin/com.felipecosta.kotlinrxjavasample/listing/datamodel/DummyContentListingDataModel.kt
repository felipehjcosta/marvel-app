package com.felipecosta.kotlinrxjavasample.listing.datamodel

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import io.reactivex.Observable
import io.reactivex.Observable.just

class DummyContentListingDataModel : ListingDataModel {

    override fun items(): Observable<List<DummyContent.DummyItem>> = just(DummyContent.ITEMS)

}