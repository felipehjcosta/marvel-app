package com.github.felipehjcosta.marvelapp.listing.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable
import javax.inject.Inject

class ListingContentDataModel @Inject constructor(
        private val characterRepository: CharacterRepository
) : ListingDataModel {

    override fun loadItems(offset: Int, limit: Int):
            Observable<List<Character>> = characterRepository.getCharacterList(offset, limit).toObservable()
}