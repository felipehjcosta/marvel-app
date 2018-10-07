package com.github.felipehjcosta.marvelapp.listing.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable

interface ListingDataModel {
    fun loadItems(offset: Int = 0, limit: Int = 20): Observable<List<Character>>
}