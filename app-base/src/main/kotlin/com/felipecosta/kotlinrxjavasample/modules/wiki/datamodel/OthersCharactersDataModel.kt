package com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable
import io.reactivex.Observable.just
import javax.inject.Inject

class OthersCharactersDataModel @Inject constructor(private val repository: DataRepository) {
    fun getOthersCharacters(): Observable<List<Character>> = just(1009664, 1009220, 1010733, 1009629, 1009175, 1009268, 1009417)
            .concatMap { repository.getCharacter(it) }
            .reduce(mutableListOf<Character>()) { acc, element -> acc.apply { add(element) } }
            .toObservable()
            .map { it }
}