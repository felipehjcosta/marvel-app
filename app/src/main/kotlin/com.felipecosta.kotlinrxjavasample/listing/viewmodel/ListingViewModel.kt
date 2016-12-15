package com.felipecosta.kotlinrxjavasample.listing.viewmodel

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import io.reactivex.Observable

class ListingViewModel {

    fun items(): Observable<List<DummyContent.DummyItem>> = Observable.just(DummyContent.ITEMS)

}