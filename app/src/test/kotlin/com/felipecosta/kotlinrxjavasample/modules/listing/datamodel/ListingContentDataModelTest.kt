package com.felipecosta.kotlinrxjavasample.modules.listing.datamodel

import com.felipecosta.kotlinrxjavasample.data.DataRepository
import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.utils.mock
import com.felipecosta.kotlinrxjavasample.utils.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

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
        whenever(listingContentDataModel.items()).thenReturn(Observable.just(listOf(character)))

        val itemsObserver = TestObserver.create<List<Character>>()

        listingContentDataModel.items().subscribe(itemsObserver)

        itemsObserver.assertValues(listOf(character))
    }
}