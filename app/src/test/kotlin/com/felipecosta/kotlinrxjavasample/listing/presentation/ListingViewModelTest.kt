package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.listing.datamodel.ListingDataModel
import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import com.felipecosta.kotlinrxjavasample.utils.mock
import com.felipecosta.kotlinrxjavasample.utils.whenever
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class ListingViewModelTest {

    lateinit var viewModel: ListingViewModel

    val dataModel: ListingDataModel = mock()

    @Before
    fun setUp() {
        viewModel = ListingViewModel(dataModel)
    }

    @Test
    fun whenCallItemsThenReturnItems() {
        whenever(dataModel.items()).thenReturn(Observable.just(DummyContent.ITEMS))

        val testObserver = TestObserver.create<List<DummyContent.DummyItem>>()

        viewModel.items().subscribe(testObserver)

        testObserver.assertValues(DummyContent.ITEMS)
    }
}