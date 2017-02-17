package com.felipecosta.kotlinrxjavasample.data

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import io.reactivex.Observable

interface DataRepository {
    fun getCharacterList(offset: Int, limit: Int): Observable<List<Character>>

    fun getCharacter(characterId: Int): Observable<Character>

    fun favoriteCharacter(characterId: Int): Observable<Boolean>
}
