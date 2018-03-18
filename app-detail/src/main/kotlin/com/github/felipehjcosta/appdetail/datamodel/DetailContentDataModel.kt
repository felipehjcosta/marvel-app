package com.github.felipehjcosta.appdetail.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable
import javax.inject.Inject

class DetailContentDataModel @Inject constructor(
        private val dataRepository: DataRepository
) : DetailDataModel {
    override fun character(characterId: Int): Observable<Character> = dataRepository
            .getCharacter(characterId)

}
