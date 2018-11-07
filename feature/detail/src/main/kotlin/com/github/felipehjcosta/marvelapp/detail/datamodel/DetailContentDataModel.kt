package com.github.felipehjcosta.marvelapp.detail.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable
import javax.inject.Inject

class DetailContentDataModel @Inject constructor(
        private val characterRepository: CharacterRepository
) : DetailDataModel {
    override fun character(characterId: Int): Observable<Character> = characterRepository
            .getCharacter(characterId)

}
