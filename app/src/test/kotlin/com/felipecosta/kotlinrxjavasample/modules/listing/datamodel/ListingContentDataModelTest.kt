package com.felipecosta.kotlinrxjavasample.modules.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*

class ListingContentDataModelTest {

    val dataRepository: DataRepository = mock()

    lateinit var listingContentDataModel: ListingContentDataModel
    @Before
    fun setUp() {
        listingContentDataModel = ListingContentDataModel(dataRepository)
    }

    @Test
    fun whenCallItemsThenReturnItems() {
        val character: Character = Character()
        whenever(dataRepository.getCharacterList(eq(0), eq(20))).thenReturn(Observable.just(listOf(character)))

        val itemsObserver = TestObserver.create<List<Character>>()

        listingContentDataModel.loadItems().subscribe(itemsObserver)

        itemsObserver.assertValues(listOf(character))
    }
}