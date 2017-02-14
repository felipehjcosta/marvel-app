package com.felipecosta.kotlinrxjavasample.modules.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

interface ListingDataModel {
    fun loadItems(offset: Int = 0, limit: Int = 20): Observable<List<Character>>
}