package com.github.felipehjcosta.marvelapp.wiki.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.CharacterRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Test

class HighlightedCharactersDataModelTest {

    private val repository = mockk<CharacterRepository>()

    private val dataModel = HighlightedCharactersDataModel(repository)

    @Test
    fun whenGetHighlightedCharactersThenAssertPredefinedCharacters() {

        val expected = listOf(1009351, 1009610, 1009718, 1009368).map {
            Character().apply {
                every { repository.getCharacter(it) } returns just(this)
            }
        }.toList()

        val itemsObserver = TestObserver.create<List<Character>>()

        dataModel.getHighlightedCharacters().subscribe(itemsObserver)

        itemsObserver.assertValue(expected)

        itemsObserver.dispose()
    }

}