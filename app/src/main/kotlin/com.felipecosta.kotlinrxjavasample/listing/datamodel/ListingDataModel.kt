package com.felipecosta.kotlinrxjavasample.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

interface ListingDataModel {
    fun items(): Observable<List<Character>>
}