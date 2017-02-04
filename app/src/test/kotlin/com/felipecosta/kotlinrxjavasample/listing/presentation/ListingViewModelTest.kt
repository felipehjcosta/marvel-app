package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.listing.model.DummyContent
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class ListingViewModelTest {

    lateinit var commandActionSubject: PublishSubject<List<DummyContent.DummyItem>>

    lateinit var viewModel: ListingViewModel

    @Before
    fun setUp() {

        commandActionSubject = PublishSubject.create()

        val asyncCommand: AsyncCommand<List<DummyContent.DummyItem>> = AsyncCommand({ commandActionSubject })

        viewModel = ListingViewModel(asyncCommand)
    }

    @Test
    fun whenCallItemsThenReturnItems() {
        val itemsObserver = TestObserver.create<List<DummyContent.DummyItem>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.listCommand.execute().subscribe()

        commandActionSubject.onNext(DummyContent.ITEMS)

        itemsObserver.assertValues(DummyContent.ITEMS)

        disposable.dispose()
    }
}