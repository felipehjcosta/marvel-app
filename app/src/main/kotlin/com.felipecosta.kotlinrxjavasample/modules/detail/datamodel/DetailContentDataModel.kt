package com.felipecosta.kotlinrxjavasample.modules.detail.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

class DetailContentDataModel : DetailDataModel {
    private val dataRepository: DataRepository

    constructor(dataRepository: DataRepository) {
        this.dataRepository = dataRepository
    }

    override fun character(characterId: Int): Observable<Character> = dataRepository.getCharacter(characterId)
}
