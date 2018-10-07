package com.github.felipehjcosta.marvelapp.listing.datamodel

import com.github.felipehjcosta.marvelapp.base.character.data.DataRepository
import com.github.felipehjcosta.marvelapp.base.character.data.pojo.Character
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable.just
import io.reactivex.observers.TestObserver
import org.junit.Test

class ListingContentDataModelTest {

    private val dataRepository = mockk<DataRepository>()

    private val listingContentDataModel = ListingContentDataModel(dataRepository)

    @Test
    fun whenCallItemsThenReturnItems() {
        val character = Character()
        every { dataRepository.getCharacterList(eq(0), eq(20)) } returns just(listOf(character))

        val itemsObserver = TestObserver.create<List<Character>>()

        listingContentDataModel.loadItems().subscribe(itemsObserver)

        itemsObserver.assertValues(listOf(character))
    }
}