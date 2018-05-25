package com.github.felipehjcosta.marvelapp.base.data

import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import io.reactivex.Observable

interface DataRepository {
    fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>>

    fun getCharacter(characterId: Int): Observable<Character>
}
