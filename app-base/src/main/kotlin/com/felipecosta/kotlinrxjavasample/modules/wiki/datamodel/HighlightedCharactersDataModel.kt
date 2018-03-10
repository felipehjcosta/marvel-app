package com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable
import io.reactivex.Observable.just
import javax.inject.Inject

class HighlightedCharactersDataModel @Inject constructor(private val repository: DataRepository) {

    fun getHighlightedCharacters(): Observable<List<Character>> = just(1009351, 1009610, 1009718, 1009368)
            .concatMap { repository.getCharacter(it) }
            .reduce(mutableListOf<Character>()) { acc, element -> acc.apply { add(element) } }
            .toObservable()
            .map { it }

}