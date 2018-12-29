package com.github.felipehjcosta.marvelapp.wiki.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.reactivex.Observable
import io.reactivex.Observable.just
import javax.inject.Inject

class OthersCharactersDataModel @Inject constructor(private val repository: CharacterRepository) {

    companion object {
        private const val THOR_CHARACTER_ID = 1009664
        private const val CAPTAIN_AMERICA_CHARACTER_ID = 1009220
        private const val STAR_LORD_CHARACTER_ID = 1010733
        private const val STORM_CHARACTER_ID = 1009629
        private const val BEAST_CHARACTER_ID = 1009175
        private const val DEADPOOL_CHARACTER_ID = 1009268
        private const val MAGNETO_CHARACTER_ID = 1009417
    }

    fun getOthersCharacters(): Observable<List<Character>> =
        just(
            THOR_CHARACTER_ID,
            CAPTAIN_AMERICA_CHARACTER_ID,
            STAR_LORD_CHARACTER_ID,
            STORM_CHARACTER_ID,
            BEAST_CHARACTER_ID,
            DEADPOOL_CHARACTER_ID,
            MAGNETO_CHARACTER_ID
        )
            .concatMap { repository.getCharacter(it).toObservable() }
            .reduce(mutableListOf<Character>()) { acc, element -> acc.apply { add(element) } }
            .toObservable()
            .map { it }
}
