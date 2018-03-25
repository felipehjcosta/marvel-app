package com.github.felipehjcosta.marvelapp.detail.datamodel

import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import io.reactivex.Observable

interface DetailDataModel {
    fun character(characterId: Int): Observable<Character>
}