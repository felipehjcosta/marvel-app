package com.felipecosta.kotlinrxjavasample.modules.highlight.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Test

class HighlightDataModelTest {

    val repository = mock<DataRepository>()

    val dataModel = HighlightDataModel(repository)

    @Test
    fun getHighlightedCharacters() {
        val hulk = Character().apply {
            name = "Hulk"
            whenever(repository.getCharacter(1009351)).thenReturn(just(this))
        }
        val spiderMan = Character().apply {
            name = "Spider-Man"
            whenever(repository.getCharacter(1009610)).thenReturn(just(this))
        }
        val wolverine = Character().apply {
            name = "Wolverine"
            whenever(repository.getCharacter(1009718)).thenReturn(just(this))
        }
        val ironMan = Character().apply {
            name = "Iron-Man"
            whenever(repository.getCharacter(1009368)).thenReturn(just(this))
        }

        val itemsObserver = TestObserver.create<List<Character>>()

        dataModel.getHighlightedCharacters().subscribe(itemsObserver)

        itemsObserver.assertValue { it == listOf(hulk, spiderMan, wolverine, ironMan) }

        itemsObserver.dispose()
    }

}