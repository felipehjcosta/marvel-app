package com.felipecosta.kotlinrxjavasample.listing.presentation

import com.felipecosta.kotlinrxjavasample.data.pojo.Character
import com.felipecosta.kotlinrxjavasample.rx.AsyncCommand
import com.felipecosta.kotlinrxjavasample.utils.mock
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class ListingViewModelTest {

    lateinit var commandActionSubject: PublishSubject<List<Character>>

    lateinit var viewModel: ListingViewModel

    @Before
    fun setUp() {

        commandActionSubject = PublishSubject.create()

        val asyncCommand: AsyncCommand<List<Character>> = AsyncCommand({ commandActionSubject })

        viewModel = ListingViewModel(asyncCommand)
    }

    @Test
    fun whenCallItemsThenReturnItems() {

        val mockedCharacter: Character = mock()
        val itemsObserver = TestObserver.create<List<Character>>()

        viewModel.items.subscribe(itemsObserver)

        val disposable = viewModel.listCommand.execute().subscribe()

        commandActionSubject.onNext(listOf(mockedCharacter))

        itemsObserver.assertValues(listOf(mockedCharacter))

        disposable.dispose()
    }
}