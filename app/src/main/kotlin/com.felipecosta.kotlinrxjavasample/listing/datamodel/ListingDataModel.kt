package com.felipecosta.kotlinrxjavasample.listing.datamodel

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import io.reactivex.Observable

interface ListingDataModel {
    fun items(): Observable<List<DummyContent.DummyItem>>
}