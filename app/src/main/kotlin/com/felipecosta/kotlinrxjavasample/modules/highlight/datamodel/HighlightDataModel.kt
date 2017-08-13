package com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

class HighlightDataModel {

    fun getHighlightedCharacters(): Observable<List<Character>> = Observable.empty()

}