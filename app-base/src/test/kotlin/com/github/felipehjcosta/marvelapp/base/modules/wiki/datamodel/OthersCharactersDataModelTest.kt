package com.github.felipehjcosta.marvelapp.base.modules.wiki.datamodel

import com.github.felipehjcosta.marvelapp.base.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.data.pojo.Character
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Test

class OthersCharactersDataModelTest {

    private val repository = mockk<DataRepository>()

    private val dataModel = OthersCharactersDataModel(repository)

    @Test
    fun whenGetOthersCharactersThenAssertPredefinedCharacters() {
        val expected = listOf(1009664, 1009220, 1010733, 1009629, 1009175, 1009268, 1009417).map {
            Character().apply {
                every { repository.getCharacter(it) } returns just(this)
            }
        }.toList()

        val itemsObserver = TestObserver.create<List<Character>>()

        dataModel.getOthersCharacters().subscribe(itemsObserver)

        itemsObserver.assertValue(expected)

        itemsObserver.dispose()
    }
}