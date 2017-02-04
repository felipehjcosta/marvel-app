package com.felipecosta.kotlinrxjavasample.modules.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

class ListingContentDataModel(private val dataRepository: DataRepository) : ListingDataModel {

    override fun items(): Observable<List<Character>> = dataRepository.getCharacterList(0, 10)
}