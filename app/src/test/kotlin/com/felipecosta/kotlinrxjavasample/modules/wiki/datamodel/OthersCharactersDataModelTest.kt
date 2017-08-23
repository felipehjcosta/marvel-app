package com.felipecosta.kotlinrxjavasample.modules.wiki.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Test

class OthersCharactersDataModelTest {

    val repository = mock<DataRepository>()

    val dataModel = OthersCharactersDataModel(repository)

    @Test
    fun whenGetOthersCharactersThenAssertPredefinedCharacters() {
        val expected = listOf(1009664, 1009220, 1010733, 1009629, 1009175, 1009268, 1009417).map {
            Character().apply {
                whenever(repository.getCharacter(it)).thenReturn(just(this))
            }
        }.toList()

        val itemsObserver = TestObserver.create<List<Character>>()

        dataModel.getOthersCharacters().subscribe(itemsObserver)

        itemsObserver.assertValue(expected)

        itemsObserver.dispose()
    }
}