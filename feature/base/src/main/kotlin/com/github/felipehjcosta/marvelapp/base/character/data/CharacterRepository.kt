package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable

interface CharacterRepository {
    fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>>

    fun getCharacter(characterId: Int): Observable<Character>
}
