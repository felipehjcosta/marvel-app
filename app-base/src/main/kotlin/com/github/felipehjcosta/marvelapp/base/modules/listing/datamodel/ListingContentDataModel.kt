package com.github.felipehjcosta.marvelapp.base.modules.listing.datamodel

import com.github.felipehjcosta.marvelapp.base.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import io.reactivex.Observable
import javax.inject.Inject

class ListingContentDataModel @Inject constructor(
        private val dataRepository: DataRepository
) : ListingDataModel {

    override fun loadItems(offset: Int, limit: Int):
            Observable<List<Character>> = dataRepository.getCharacterList(offset, limit)
}