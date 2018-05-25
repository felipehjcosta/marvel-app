package com.github.felipehjcosta.marvelapp.detail.datamodel

import com.github.felipehjcosta.marvelapp.base.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import io.reactivex.Observable
import javax.inject.Inject

class DetailContentDataModel @Inject constructor(
        private val dataRepository: DataRepository
) : DetailDataModel {
    override fun character(characterId: Int): Observable<Character> = dataRepository
            .getCharacter(characterId)

}
