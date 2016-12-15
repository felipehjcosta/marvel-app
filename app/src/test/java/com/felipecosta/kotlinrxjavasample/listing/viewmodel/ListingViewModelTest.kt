package com.felipecosta.kotlinrxjavasample.listing.viewmodel

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class ListingViewModelTest {

    lateinit var viewModel: ListingViewModel

    @Before
    fun setUp() {
        viewModel = ListingViewModel()
    }

    @Test
    fun whenCallItemsThenReturnItems() {
        val testObserver = TestObserver.create<List<DummyContent.DummyItem>>()

        viewModel.items().subscribe(testObserver)

        testObserver.assertValues(DummyContent.ITEMS)
    }
}