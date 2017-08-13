package com.felipecosta.kotlinrxjavasample.modules.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

class ListingContentDataModel(private val dataRepository: DataRepository) : ListingDataModel {

    override fun loadItems(offset: Int, limit: Int):
            Observable<List<Character>> = dataRepository.getCharacterList(offset, limit)
}