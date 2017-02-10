package com.felipecosta.kotlinrxjavasample.detail.datamodel

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

interface DetailDataModel {
    fun character(characterId: Int): Observable<Character>
}