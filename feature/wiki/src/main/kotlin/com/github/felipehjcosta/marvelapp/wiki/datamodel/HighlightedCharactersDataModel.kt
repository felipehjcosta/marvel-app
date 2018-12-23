package com.github.felipehjcosta.marvelapp.wiki.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable
import io.reactivex.Observable.just
import javax.inject.Inject

class HighlightedCharactersDataModel @Inject constructor(private val repository: CharacterRepository) {

    companion object {
        private const val HULK_CHARACTER_ID = 1009351
        private const val SPIDER_MAN_CHARACTER_ID = 1009610
        private const val WOLVERINE_CHARACTER_ID = 1009718
        private const val IRON_MAN_CHARACTER_ID = 1009368
    }

    fun getHighlightedCharacters(): Observable<List<Character>> =
        just(
            HULK_CHARACTER_ID,
            SPIDER_MAN_CHARACTER_ID,
            WOLVERINE_CHARACTER_ID,
            IRON_MAN_CHARACTER_ID
        )
            .concatMap { repository.getCharacter(it).toObservable() }
            .reduce(mutableListOf<Character>()) { acc, element -> acc.apply { add(element) } }
            .toObservable()
            .map { it }

}
