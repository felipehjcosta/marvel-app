package com.github.felipehjcosta.marvelapp.base.character.data

import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Single

interface CharacterRepository {
    fun getCharacterList(offset: Int, limit: Int): Single<List<Character>>

    fun getCharacter(characterId: Int): Single<Character>
}
